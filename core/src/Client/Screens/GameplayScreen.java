package Client.Screens;

import Client.Client;
import Model.LogicalHeros.LogicalHero;
import Model.LogicalPlayer;
import Model.Move;
import Client.GameEngine;
import Client.GraphicalHeroes.*;
import Client.Map.Highlight;
import Client.Map.Obstacle;
import Client.GraphicalSkills.Skill;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.StrategicGame;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends AbstractScreen{
    //private Hero entity;
    private List<Boolean> buttonPressed;
    public static int STATE=0;
    //private Button entityButton;
    //private Stage backgroundStage;
    public static List<Button> buttonList = new ArrayList<>();
    public LogicalHero activeHero;
    public int activeSkillIndex;
    public LogicalPlayer activePlayer;
    private GameEngine gameEngine;
    Client client;
    public static boolean flag;
    public GameplayScreen(StrategicGame game) throws Exception {
        super(game);
    }

    @Override
    protected void init() throws Exception {
        gameEngine = new GameEngine();
        client = new Client(this.game,true);
        while (!flag){
            System.out.println("waiting...");
        }

        initGameEngine();

    }
    private void initGameEngine(){
        //Testing
        gameEngine= new GameEngine(client.received);
        StrategicGame.gameEngine = gameEngine;
        System.out.println(gameEngine);
        List<Hero> heros=new ArrayList<>();
        List<Obstacle> obstacles=new ArrayList<>();
        for (int yi = 0; yi < GameEngine.getGraphGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < GameEngine.getGraphGameMap().getMaxX(); xi++){
                stage.addActor(GameEngine.getGraphGameMap().getFieldAt(yi,xi));
                if(GameEngine.getGraphGameMap().getFieldAt(yi,xi).getHero()!=null){
                    heros.add(GameEngine.getGraphGameMap().getFieldAt(yi,xi).getHero());
                }
                if(GameEngine.getGraphGameMap().getFieldAt(yi,xi).getObstacle()!=null){
                    obstacles.add(GameEngine.getGraphGameMap().getFieldAt(yi,xi).getObstacle());
                }

            }
        for (Obstacle obstacle:obstacles) {
            stage.addActor(obstacle);
        }
        for (Hero hero:heros) {
            stage.addActor(hero);
        }



    }
    //Calculate (render) all moves
    @Override
    public void render(float delta){
        super.render(delta);
        update();

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }
    private void update(){
        collectMoves();
        rightClickMenu();
        removeDeadHeroesFromStage();
        stage.act();
    }
    private void rightClickMenu(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            //STATE = 0;
            buttonPressed = new ArrayList<>();
            clearButtons();
            clearHighlights();
            List<Skill> skillList;
            int x = (Gdx.input.getX() - 10) / 32;
            int y = (Gdx.input.getY() - 10) / 32;
            for (final Actor actor : stage.getActors()) {

                if (validateInput(actor.getX(),actor.getY(),x,y) &&
                        actor.getClass().getSuperclass().equals(Hero.class)) {
                    STATE = 1;
                    clearHighlights();
                    clearButtons();
                    skillList = GameEngine.getPossibleSkills((Hero) actor);
                    buttonList = new ArrayList<>();
                    int iterator = 0;
                    Skin skin =new Skin(Gdx.files.internal("skin/comic-ui.json"));
                    for (final Skill skill : skillList) {
                        buttonList.add(new TextButton((skill.getClass().toString().substring(28)),skin));
                        buttonList.get(iterator).setWidth(300);
                        buttonList.get(iterator).setHeight(64);
                        buttonList.get(iterator).setX(StrategicGame.CONTROLPANELX);
                        buttonList.get(iterator).setY(StrategicGame.HEIGHT - (80 + iterator * (64 + 5)));
                        buttonList.get(iterator).setDebug(false);//TODO false in this place
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
                                        stage.addActor(new Highlight("highlight.png", ints[1], ints[0]));

                                    }
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), true);
                                    makeOtherButtonsFalse(skill.getIndex());
                                    actor.remove();
                                    stage.addActor(actor);
                                    activeHero = GameEngine.locateLogHero((Hero)actor);
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
                STATE=0;
            }

        }

    }
    private void clearHighlights(){
        for(int i=0;i<stage.getActors().size;i++){
            if(stage.getActors().get(i).getClass().equals(Highlight.class)){
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }
    private void clearButtons(){
        buttonList = new ArrayList<>();
        for(int i=0;i<stage.getActors().size;i++){
            if(stage.getActors().get(i).getClass().equals(TextButton.class)){
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }
    private boolean validateInput(float x,float y,int xm,int ym){
        return xm == GameEngine.guiToMapConvert((int) x, (int) y)[0] &&
                ym == GameEngine.guiToMapConvert((int) x, (int) y)[1];
    }
    private void makeOtherButtonsFalse(int index){
        for(int i=0;i<buttonPressed.size();i++){
            if(i!=index){
                buttonPressed.remove(i);
                buttonPressed.add(i,false);
            }
        }
    }
    //TODO make hero corpses and show them after the hit
    private void removeDeadHeroesFromStage(){
        for(int i=0;i<stage.getActors().size;i++){
            if(stage.getActors().get(i).getClass().getSuperclass().equals(Hero.class) &&
                    !((Hero)stage.getActors().get(i)).isAlive()){
                stage.getActors().get(i).remove();
                i--;
            }

        }
    }
    private void collectMoves(){
        if(STATE == 2){
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                int x=(Gdx.input.getX()-10)/32;
                int y=(Gdx.input.getY()-10)/32;
                if(x>=22) return;//x=21;
                if(y>=22) return;//y=21;
                if(x<0) return;//x=0;
                if(y<0) return;//y=0;
                for(Actor actor:stage.getActors()){
                    if(validateInput(actor.getX(),actor.getY(),x,y) && actor.getClass().equals(Highlight.class)){
                        // TODO change active player to real active player
                        GameEngine.addActionToQueue(new Move(activePlayer,activeHero, activeSkillIndex,y,x));
                        clearButtons();
                        clearHighlights();
                    }
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
        return stage.touchDown(screenX,screenY,pointer,button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX,screenY,pointer,button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX,screenY,pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX,screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        if(amount == 1 /*&& stage.keyTyped((char) Input.Keys.CONTROL_LEFT)*/){
            super.camera.zoom += .2f;
        }
        else if(amount == -1 /*&& stage.keyTyped((char) Input.Keys.CONTROL_LEFT)*/){
            super.camera.zoom -= .2f;
        }

        return false;

    }


}
