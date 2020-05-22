package Client.Screens;

import Client.CorrelationUtils;
import Client.GameEngine;
import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.Skill;
import Client.Map.Highlight;
import Client.Map.Obstacle;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalPlayer;
import Model.Move;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.StrategicGame;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends AbstractScreen {
    private List<Boolean> buttonPressed;
    public static int STATE = 0;
    public static List<Button> buttonList = new ArrayList<>();
    public LogicalHero activeHero;
    public int activeSkillIndex;
    public LogicalPlayer activePlayer;
    private GameEngine gameEngine;
    public static boolean freshUpdate;
    private int moveCounter = 0;
    private CheckBox checkBox;
    private String readyToSend = "Collecting moves...";

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
        background.setSize(game.WIDTH, game.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }
    private void addIndicators(){
        Skin skin = new Skin(Gdx.files.internal("skin/craftacular/skin/craftacular-ui.json"));
        TextField textField = new TextField(activePlayer.getNick(), skin);
        textField.setWidth(300);
        textField.setHeight(64);
        textField.setX(StrategicGame.CONTROLPANELX);
        textField.setY(StrategicGame.HEIGHT - 70);
        textField.setDisabled(true);
        skin = new Skin(Gdx.files.internal("skin/comic/comic-ui.json"));
        checkBox = new CheckBox("Send to server",skin);
        checkBox.setX(StrategicGame.CONTROLPANELX);
        checkBox.setY(50);
        checkBox.setChecked(false);
        checkBox.setDisabled(true);
        stage.addActor(checkBox);
        stage.addActor(textField);
    }

    private void initGameEngine() {
        gameEngine = new GameEngine(StrategicGame.client.receivedMap);
        StrategicGame.gameEngine = gameEngine;

        List<Hero> heros = new ArrayList<>();
        List<Obstacle> obstacles = new ArrayList<>();
        for (int yi = 0; yi < GameEngine.getGraphGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < GameEngine.getGraphGameMap().getMaxX(); xi++) {
                stage.addActor(GameEngine.getGraphGameMap().getFieldAt(yi, xi));
                if (GameEngine.getGraphGameMap().getFieldAt(yi, xi).getHero() != null) {
                    heros.add(GameEngine.getGraphGameMap().getFieldAt(yi, xi).getHero());
                }
                if (GameEngine.getGraphGameMap().getFieldAt(yi, xi).getObstacle() != null) {
                    obstacles.add(GameEngine.getGraphGameMap().getFieldAt(yi, xi).getObstacle());
                }

            }
        for (Obstacle obstacle : obstacles) {
            stage.addActor(obstacle);
        }
        for (Hero hero : heros) {
            stage.addActor(hero);
        }


    }
    @Override
    public void render(float delta) {
        super.render(delta);
        update();

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }

    private void update() {
        collectMoves();
        rightClickMenu();
        handleFreshUpdate();
        highlightPlayersHeroes();
        stage.act();
    }

    private void handleFreshUpdate() {
        if (freshUpdate) {
            System.out.println(gameEngine.getLogGameMap());
            checkBox.setChecked(false);
            clearHighlights();
            for (Actor actor : stage.getActors()) {
                if (actor instanceof Hero)
                    changeSkinOfHeroes((Hero) actor);
            }
            moveCounter = 0;
            freshUpdate = false;
        }
    }

    private void changeSkinOfHeroes(Hero hero) {
        if (hero.isAlive()) hero.setAliveTexture();
        else hero.setDeadTexture();
    }

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
                        ((Hero) actor).getOwner().equalToLogicalPlayer(activePlayer)) {
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
                        buttonList.get(iterator).setY(StrategicGame.HEIGHT - 84 -(80 + iterator * (64 + 5)));
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
                                    activeSkillIndex = -1;

                                } else {
                                    for (int[] ints : GameEngine.getPossibleTargets((Hero) actor, skill.getIndex())) {
                                        stage.addActor(new Highlight("fieldGraphics/highlight.png", ints[1], ints[0]));

                                    }
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), true);
                                    makeOtherButtonsFalse(skill.getIndex());
                                    actor.remove();
                                    stage.addActor(actor);
                                    activeHero = CorrelationUtils.locateLogHero((Hero) actor);
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

    private void clearHighlights() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof Highlight) {
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }

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

    private boolean validateInput(float x, float y, int xm, int ym) {
        return xm == CorrelationUtils.guiToMapConvert((int) x, (int) y)[0] &&
                ym == CorrelationUtils.guiToMapConvert((int) x, (int) y)[1];
    }

    private void makeOtherButtonsFalse(int index) {
        for (int i = 0; i < buttonPressed.size(); i++) {
            if (i != index) {
                buttonPressed.remove(i);
                buttonPressed.add(i, false);
            }
        }
    }

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
                    if (validateInput(actor.getX(), actor.getY(), x, y) && actor.getClass().equals(Highlight.class)) {
                        STATE = 1;
                        moveCounter++;
                        GameEngine.addActionToQueue(new Move(activePlayer, activeHero, activeSkillIndex, y, x));
                        clearButtons();
                        clearHighlights();
                    }
                }
            }
            if(moveCounter == StrategicGame.movesPerTour){
                checkBox.setChecked(true);
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
        if (amount == 1 /*&& stage.keyTyped((char) Input.Keys.CONTROL_LEFT)*/) {
            super.camera.zoom += .2f;
        } else if (amount == -1 /*&& stage.keyTyped((char) Input.Keys.CONTROL_LEFT)*/) {
            super.camera.zoom -= .2f;
        }

        return false;

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }


}
