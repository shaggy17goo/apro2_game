package Screens;

import Client.GUI.Move;
import Client.Model.GameEngine;
import Client.Model.graphicalheros.*;
import Client.Model.map.Highlight;
import Client.Model.map.Obstacle;
import Client.Model.map.Wall;
import Client.Model.Player;
import Client.Model.Skills.Skill;
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
    public Hero activeHero;
    public int activeSkillNumber;
    public Player activePlayer;
    public GameplayScreen(StrategicGame game) {
        super(game);
    }
    private GameEngine gameEngine;
    @Override
    protected void init(){
        //initEntity();
        initGameEngine();
        //initEntityButton();
    }
    private void initGameEngine(){
        //Testing
            activePlayer=new Player();
            Player player2=new Player();
            //Wizard wizz=new Wizard(10,10);
            //Paladin pall=new Paladin(3,5);
            //Warrior warr=new Warrior(5,7);
            //Necromancer necc = new Necromancer(5,5);
            Archer archer=new Archer(5,5);
            Paladin paladin=new Paladin(15,15);
            Wizard wizard=new Wizard(10,10);
            Warrior warrior=new Warrior(10,5);
            Necromancer necromancer=new Necromancer(5,10);
            Priest priest = new Priest(15,11);
            Uszatek uszatek = new Uszatek(21,21);
            player2.addHero(archer);
            player2.addHero(paladin);
            player2.addHero(necromancer);
            activePlayer.addHero(wizard);
            activePlayer.addHero(warrior);
            activePlayer.addHero(priest);
            activePlayer.addHero(uszatek);
            Wall wall1=new Wall(11,10);
            Wall wall2=new Wall(11,11);
            Wall wall3=new Wall(11,9);
            Wall wall4=new Wall(10,9);
            Wall wall5=new Wall(10,11);
            Wall wall6=new Wall(9,9);
            Wall wall7=new Wall(9,10);
            Wall wall8=new Wall(9,11);
            //player.addHero(pall);
            //player.addHero(wizz);
            //player.addHero(necc);
        List<Hero> heros=new ArrayList<>();
        List<Obstacle> obstacles=new ArrayList<>();
        gameEngine=new GameEngine(22,22);
        GameEngine.addHero(archer);
        GameEngine.addHero(wizard);
        GameEngine.addHero(paladin);
        GameEngine.addHero(warrior);
        GameEngine.addHero(necromancer);
        GameEngine.addHero(priest);
        GameEngine.addHero(uszatek);
        //GameEngine.addObstacle(wall1);
        GameEngine.addObstacle(wall2);
        GameEngine.addObstacle(wall3);
        GameEngine.addObstacle(wall4);
        GameEngine.addObstacle(wall5);
        GameEngine.addObstacle(wall6);
        GameEngine.addObstacle(wall7);
        GameEngine.addObstacle(wall8);
        for (int yi = 0; yi < GameEngine.getGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < GameEngine.getGameMap().getMaxX(); xi++){
                stage.addActor(GameEngine.getGameMap().getFieldAt(yi,xi));
                if(GameEngine.getGameMap().getFieldAt(yi,xi).getHero()!=null){
                    heros.add(GameEngine.getGameMap().getFieldAt(yi,xi).getHero());
                }
                if(GameEngine.getGameMap().getFieldAt(yi,xi).getObstacle()!=null){
                    obstacles.add(GameEngine.getGameMap().getFieldAt(yi,xi).getObstacle());
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

        collectMoves();
        rightClickMenu();
        removeDeadHerosFromStage();
        update();

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
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
                        buttonList.add(new TextButton((skill.getClass().toString().substring(26)),skin));
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
                                    activeSkillNumber = -1;

                                } else {
                                    for (int[] ints : GameEngine.getPossibleTargets((Hero) actor, skill.getIndex())) {
                                        stage.addActor(new Highlight("highlight.png", ints[1], ints[0]));

                                    }
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), true);
                                    makeOtherButtonsFalse(skill.getIndex());
                                    actor.remove();
                                    stage.addActor(actor);
                                    activeHero = (Hero)actor;
                                    activeSkillNumber = skill.getIndex();
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
    //TODO make hero corpses
    private void removeDeadHerosFromStage(){
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
                        GameEngine.addActionToQueue(new Move(activePlayer,activeHero,activeSkillNumber,y,x));
                        clearButtons();
                        clearHighlights();
                        /*if(activeHero.getSkillsList().get(activeSkillNumber) instanceof Fireball)
                            activeHero.getSkillsList().get(activeSkillNumber).remove();*/
                    }
                }
            }
        }
    }
    private void update(){
        stage.act();
    }

}
