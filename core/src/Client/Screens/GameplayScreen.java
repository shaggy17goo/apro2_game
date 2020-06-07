package Client.Screens;

import Client.CorrelationUtils;
import Client.GameEngine;
import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.*;
import Client.Map.*;
import Client.MathUtils;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalPlayer;
import Model.Move;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.StrategicGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen where most of the game happens. Collects input and sends it to appropriate destination
 */
public class GameplayScreen extends AbstractScreen {
    public static boolean freshUpdate = true; //flag from gameServer saying that the client got a fresh map and moves to load
    public static List<int[]> wallsToPlace= new ArrayList<>();
    public static List<int[]> trapsToPlace= new ArrayList<>();

    private LogicalHero activeHero; // hero being picked
    private Hero activeGraphicalHero;
    private int activeSkillIndex; // index of skill being picked
    private LogicalPlayer activePlayer; // player who owns this client
    private List<Button> buttonList = new ArrayList<>(); // list of buttons with skills
    private int STATE = 0; // We thought about handling GUI like Finite-state machine so it's a state of that
    // imaginary machine
    private GameEngine gameEngine; // GameEngine to calculate things to show
    private int moveCounter = 0; // When it hits StrategicGame.movesPerTour = 4 a player can't move anymore (in this turn)
    private CheckBox checkBox; // Checkbox to show if moves where send to server
    private List<Boolean> buttonPressed; // Only one should be true at once
    private List<Hero> usedHeroes = new ArrayList<>(); // Which heroes were already used

    public GameplayScreen(StrategicGame game) throws Exception {
        super(game);
    }

    @Override
    protected void init() {
        activePlayer = game.logicalPlayer;
        addBackground();
        addIndicators();
        initGameEngine();


    }

    private void addBackground() {
        TextureRegion textureRegion = new TextureRegion(new Texture("screenGraphics/gameBackground.png"));
        final Image background = new Image(textureRegion);
        background.setSize(StrategicGame.WIDTH, StrategicGame.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }

    /**
     * Add TextField with nick and checkbox indicating whether the message was send to the server
     */
    private void addIndicators() {
        Skin skin = new Skin(Gdx.files.internal("skin/craftacular/skin/craftacular-ui.json"));
        TextField textField = new TextField(activePlayer.getNick(), skin);
        textField.setWidth(300);
        textField.setHeight(64);
        textField.setX(StrategicGame.CONTROLPANELX);
        textField.setY(StrategicGame.HEIGHT - 70);
        textField.setDisabled(true);
        skin = new Skin(Gdx.files.internal("skin/comic/comic-ui.json"));
        checkBox = new CheckBox("Send to server", skin);
        checkBox.setX(StrategicGame.CONTROLPANELX);
        checkBox.setY(50);
        checkBox.setChecked(false);
        checkBox.setDisabled(true);
        stage.addActor(checkBox);
        stage.addActor(textField);
    }

    /**
     * Read new gameMap received from the server and add actors where needed
     */
    private void initGameEngine() {
        gameEngine = new GameEngine(StrategicGame.client.receivedMap);
        StrategicGame.gameEngine = gameEngine;

        List<Hero> heroes = new ArrayList<>();
        List<Obstacle> obstacles = new ArrayList<>();
        for (int yi = 0; yi < GameEngine.getGraphGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < GameEngine.getGraphGameMap().getMaxX(); xi++) {
                stage.addActor(GameEngine.getGraphGameMap().getFieldAt(yi, xi));
                if (GameEngine.getGraphGameMap().getFieldAt(yi, xi).getHero() != null) {
                    heroes.add(GameEngine.getGraphGameMap().getFieldAt(yi, xi).getHero());
                }
                if (GameEngine.getGraphGameMap().getFieldAt(yi, xi).getObstacle() != null) {
                    obstacles.add(GameEngine.getGraphGameMap().getFieldAt(yi, xi).getObstacle());
                }

            }
        for (Obstacle obstacle : obstacles) {
            stage.addActor(obstacle);
        }
        for (Hero hero : heroes) {
            stage.addActor(hero);
        }


    }


    /**
     * Render the image 60 times per second
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        update();

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }

    /**
     * Update the image
     */
    private void update() {
        collectMoves();
        rightClickMenu();
        handleFreshUpdate();
        highlightPlayersHeroes();
        handleHeroWalls();
        handleTraps();
        stage.act();
    }

    /**
     * Called when fresh update from server arrives
     */
    private void handleFreshUpdate() {
        if (freshUpdate) {
            System.out.println(GameEngine.getLogGameMap());
            checkBox.setChecked(false);
            clearHighlights();
            clearTransparentHeroes();
            clearHPBars();
            usedHeroes = new ArrayList<>();
            Hero tempHero;
            for (Actor actor : stage.getActors()) {
                if (actor instanceof Hero) {
                    tempHero = (Hero) actor;
                    adjustNumberOfMoves(tempHero);
                    changeSkinOfHeroes(tempHero);
                    addHPBars(tempHero);
                    tempHero.setLastSeenAlive(tempHero.isAlive());
                }


            }
            moveCounter = 0;
            freshUpdate = false;

            try {
                if (gameEngine.onePlayerLiveOn()) {
                    game.setScreen(new EndGameScreen(game));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Display HP bars with heroes' current HP
     */
    private void addHPBars(Hero hero) {
        stage.addActor(new DamageBar(hero));
        stage.addActor(new HealthBar(hero));
    }

    /**
     * Change textures of heroes depending on if they are alive
     */
    private void changeSkinOfHeroes(Hero hero) {
        if (hero.isAlive()) hero.setAliveTexture();
        else hero.setDeadTexture();
    }
    private void handleHeroWalls(){
        if (!wallsToPlace.isEmpty()) {
            DestroyableWall wall;
            for(int[] ints:wallsToPlace){
                wall = new DestroyableWall(ints[0],ints[1]);
                stage.addActor(wall);
                GameEngine.getGraphGameMap().getFieldAt(ints[0],ints[1]).addObstacle(wall);
                GameEngine.destroyableWalls.add(wall);
            }
            wallsToPlace.clear();
        }
        //Delete walls if their durability is below 0
        for(int i=0; i <GameEngine.destroyableWalls.size();i++){
            if(GameEngine.destroyableWalls.get(i).durability<=0){
                GameEngine.destroyableWalls.get(i).remove();
                GameEngine.destroyableWalls.remove(i);
                i--;
            }
        }
    }


    private void handleTraps(){
        if (!trapsToPlace.isEmpty()) {
            Trap trap;
            for(int[] ints:trapsToPlace){
                trap = new Trap(ints[0],ints[1],ints[2]);
                stage.addActor(trap);
                GameEngine.getGraphGameMap().getFieldAt(ints[0],ints[1]).addObstacle(trap);
            }
            trapsToPlace.clear();
        }
    }
    /**
     * Adjust number of moves of a player if a hero of his dies or has been resurected
     */
    private void adjustNumberOfMoves(Hero hero) {

        if (hero.getOwner().equalToLogicalPlayer(activePlayer)) {
            //If he was alive but isn't anymore
            if (!hero.isAlive() && hero.wasLastSeenAlive()) {
                StrategicGame.movesPerTour--;
                System.out.println("Dead");
            }

            //If he was dead but has been resurrected
            else if (hero.isAlive() && !hero.wasLastSeenAlive()) {
                StrategicGame.movesPerTour++;
                System.out.println("Revived");
            }

        }

    }

    /**
     * Display a highlight where player's heroes stand
     */
    private void highlightPlayersHeroes() {
        if (STATE != 2) {
            for (Actor actor : stage.getActors()) {
                if (actor instanceof Hero && ((Hero) actor).getOwner().equalToLogicalPlayer(activePlayer)) {
                    stage.addActor(new Highlight("fieldGraphics/highlightPlayerOrange.png",
                            ((Hero) actor).getMapX(), ((Hero) actor).getMapY()));

                }
            }
        }
    }

    /**
     * When players click on theirs' heroes, display a menu of heroes' skills
     */
    private void rightClickMenu() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            buttonPressed = new ArrayList<>();
            clearButtons();
            clearHighlights();
            List<Skill> skillList;
            int x = (Gdx.input.getX() - 10) / 32;
            int y = (Gdx.input.getY() - 10) / 32;
            for (final Actor actor : stage.getActors()) {

                if (validateInput(actor.getX(), actor.getY(), x, y) &&
                        actor instanceof Hero &&
                        ((Hero) actor).isAlive() &&
                        ((Hero) actor).getOwner().equalToLogicalPlayer(activePlayer) &&
                        !isHeroUsed((Hero) actor)) {
                    STATE = 1;
                    clearHighlights();
                    clearButtons();
                    skillList = GameEngine.getPossibleSkills((Hero) actor);
                    buttonList = new ArrayList<>();
                    int iterator = 0;
                    Skin skin = new Skin(Gdx.files.internal("skin/craftacular/skin/craftacular-ui.json"));
                    for (final Skill skill : skillList) {
                        buttonList.add(new TextButton((skill.getClass().toString().substring(29)), skin));
                        buttonList.get(iterator).setWidth(300);
                        buttonList.get(iterator).setHeight(64);
                        buttonList.get(iterator).setX(StrategicGame.CONTROLPANELX);
                        buttonList.get(iterator).setY(StrategicGame.HEIGHT - 84 - (80 + iterator * (64 + 5)));
                        buttonList.get(iterator).setDebug(false);
                        stage.addActor(buttonList.get(iterator));
                        buttonPressed.add(false);
                        buttonList.get(iterator).addListener(new ClickListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                clearHighlights();
                                if (buttonPressed.get(skill.getIndex())) {
                                    STATE = 1;
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), false);
                                    activeHero = null;
                                    activeGraphicalHero = null;
                                    activeSkillIndex = -1;

                                } else {
                                    for (int[] ints : GameEngine.getPossibleTargets((Hero) actor, skill.getIndex())) {
                                        stage.addActor(new Highlight("fieldGraphics/highlight.png", ints[1], ints[0]));

                                    }
                                    highlightPlayersHeroes();
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), true);
                                    makeOtherButtonsFalse(skill.getIndex());
                                    //actor.remove();
                                    //stage.addActor(actor);
                                    activeGraphicalHero = (Hero) actor;
                                    activeHero = CorrelationUtils.locateLogHero(activeGraphicalHero);
                                    activeSkillIndex = skill.getIndex();
                                }
                                STATE = 2;
                                return super.touchDown(event, x, y, pointer, button);
                            }
                        });
                        iterator++;
                    }
                    break;
                }
                STATE = 0;
            }

        }

    }
    /**
     * Display information about
     */
    private void showHeroesInfo(){
        for(LogicalHero hero:activePlayer.getHeroesList()){
            System.out.println(hero.getMapX() + " " + hero.getMapY() + hero);
        }
    }

    /**
     * Clear all transparent heroes from screen and stage
     */
    private void clearTransparentHeroes() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TransparentEntity) {
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }

    /**
     * Clear all HP bars before a new update
     */
    private void clearHPBars() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof HealthBar ||
                    stage.getActors().get(i) instanceof DamageBar) {
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }

    /**
     * Clear all highlights from screen and stage
     */
    private void clearHighlights() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof Highlight) {
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }

    /**
     * Clear all buttons
     */
    private void clearButtons() {
        buttonList = new ArrayList<>();
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextButton &&
                    !(stage.getActors().get(i) instanceof CheckBox)) {
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }

    /**
     * Check if x,y of mouse corresponds to a given mapX,mapY
     */
    private boolean validateInput(float x, float y, int xm, int ym) {
        return xm == CorrelationUtils.guiToMapConvert((int) x, (int) y)[0] &&
                ym == CorrelationUtils.guiToMapConvert((int) x, (int) y)[1];
    }

    /**
     * Iterate the buttons list and make all of the, false besides
     */
    private void makeOtherButtonsFalse(int index) {
        for (int i = 0; i < buttonPressed.size(); i++) {
            if (i != index) {
                buttonPressed.remove(i);
                buttonPressed.add(i, false);
            }
        }
    }

    private boolean isHeroUsed(LogicalHero logicalHero) {
        for (Hero hero : usedHeroes) {
            if (hero.equalToLogical(logicalHero)) {
                System.out.println("true");
                return true;
            }
        }
        return false;
    }

    private boolean isHeroUsed(Hero hero) {
        for (Hero hero1 : usedHeroes) {
            if (hero1.equals(hero)) {
                return true;
            }
        }
        return false;
    }

    /**
     * If a player Left-cick on a highlighted field, validate input and add to turn
     *
     * @return
     */
    private void collectMoves() {
        if (STATE == 2) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && moveCounter < StrategicGame.movesPerTour) {
                int x = (Gdx.input.getX() - 10) / 32;
                int y = (Gdx.input.getY() - 10) / 32;
                //if mouse pointer is outside of playable area, return
                if (x >= 22) return;
                if (y >= 22) return;
                if (x < 0) return;
                if (y < 0) return;
                for (Actor actor : stage.getActors()) {
                    if (validateInput(actor.getX(), actor.getY(), x, y) && actor instanceof Highlight &&
                            !isHeroUsed(activeHero)) {
                        STATE = 1;
                        moveCounter++;
                        GameEngine.addActionToQueue(new Move(activePlayer, activeHero, activeSkillIndex, y, x));
                        //If hero will be moved, add an indicator
                        Skill skill = activeGraphicalHero.getSkillsList().get(activeSkillIndex);
                        String imagePath = "";
                        //If skill is not a projectile
                        if (skill instanceof Walk)
                            imagePath = activeGraphicalHero.getImagePath();
                        else if (skill instanceof PlaceTrap)
                            imagePath = "skillGraphics/trap.png";
                        else if (skill instanceof AreaHeal)
                            imagePath = "skillGraphics/heal.png";
                        else if (skill instanceof Heal)
                            imagePath = "skillGraphics/heal.png";
                        else if (skill instanceof Jump)
                            imagePath = "skillGraphics/jump.png";
                        else if (skill instanceof Necromancy)
                            imagePath = "skillGraphics/Necromancy.png";
                        else if (skill instanceof PlaceWall)
                            imagePath = "skillGraphics/DesWall.png";
                        else if (skill instanceof Stay)
                            imagePath = "skillGraphics/stay.png";
                        else if (skill instanceof Teleport)
                            imagePath = "skillGraphics/teleport.png";
                        //The skill is a projectile
                        else {
                            if (skill instanceof Arrow)
                                imagePath = "skillGraphics/arrow.png";
                            if (skill instanceof ArrowVolley)
                                imagePath = "skillGraphics/arrowVolley.png";
                            if (skill instanceof Fireball)
                                imagePath = "skillGraphics/fireBallDirection.png";
                            if (skill instanceof Melee)
                                imagePath = "skillGraphics/Melee.png";
                            if (!imagePath.equals("")) {
                                Actor actor1 = new TransparentEntity(TransparentEntity.transparentEntity(imagePath), x, y);
                                actor1.setRotation(0);
                                int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
                                actor1.rotateBy((float) MathUtils.getDegreeBetween((int) activeGraphicalHero.getY(),
                                        (int) activeGraphicalHero.getX(), coords[1], coords[0]));
                                stage.addActor(actor1);
                                imagePath = "";
                            }
                        }
                        //Displaying for non - projectile skills
                        if (!imagePath.equals("")) {
                            Actor actor1 = new TransparentEntity(TransparentEntity.transparentEntity(imagePath), x, y);
                            stage.addActor(actor1);
                        }
                        usedHeroes.add(activeGraphicalHero);
                        clearButtons();
                        clearHighlights();
                    }
                }
                if (moveCounter == StrategicGame.movesPerTour) {
                    Sound blaster = Gdx.audio.newSound(Gdx.files.internal("soundEffects/blaster.mp3"));
                    long id = blaster.play();
                    blaster.setVolume(id, 0.2f);
                    checkBox.setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return stage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        /*if (amount == 1) {
            super.camera.zoom += .2f;
        } else if (amount == -1) {
            super.camera.zoom -= .2f;
        }*/

        return false;

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }


}
