package Screens;

import Client.Model.GameEngine;
import Client.Model.Heros.*;
import Client.Model.Map.Highlight;
import Client.Model.Map.Obstacle;
import Client.Model.Map.Wall;
import Client.Model.Player;
import Client.Model.Skills.Skill;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
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
            Player player=new Player();
            //Wizard wizz=new Wizard(10,10);
            //Paladin pall=new Paladin(3,5);
            //Warrior warr=new Warrior(5,7);
            //Necromancer necc = new Necromancer(5,5);
            Archer archer=new Archer(5,5);
            Wizard wizard=new Wizard(10,10);
            player.addHero(archer);
            player.addHero(wizard);
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
    /*private void initEntity(){
        entity = new Archer(5,5);
        //stage.addActor(entity);
    }*/
    /*private void initEntityButton(){
        entityButton = new Button(new ButtonStyle());//invisible style
        entityButton.setWidth(entity.getWidth());
        entityButton.setHeight(entity.getHeight());
        entityButton.setX(entity.getX());
        entityButton.setY(entity.getY());
        entityButton.setDebug(true);//TODO false in this place
        stage.addActor(entityButton);
        entityButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                entity.reactOnClick();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }*/
    //Calculate (render) all moves
    @Override
    public void render(float delta){
        super.render(delta);
        // For testing move
 /*       if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            int x=(Gdx.input.getX()-10)/32;
            int y=(Gdx.input.getY()-10)/32;
            System.out.println(x+ " "+ y);
            if(x>=22) x=21;
            if(y>=22) y=21;
            if(x<0) x=0;
            if(y<0) y=0;
            entity.reactOnClick(x,y);
        }*/

        /*if(STATE<1) {
            clearHighlights();
            clearButtons();
        }*/


        rightClickMenu();
        update();
        spriteBatch.begin();

        stage.draw();
        /*for(Sprite sprite : sprites){
            //spriteBatch.draw(new Texture("highlight.png"),150,150);
            sprite.draw(spriteBatch);
        }*/
        //entity.draw(spriteBatch,1);
        spriteBatch.end();
    }
    private void rightClickMenu(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            STATE = 0;
            buttonPressed = new ArrayList<>();
            clearButtons();
            clearHighlights();
            List<Skill> skillList;
            int x = (Gdx.input.getX() - 10) / 32;
            int y = (Gdx.input.getY() - 10) / 32;
            for (final Actor actor : stage.getActors()) {

                if (x == GameEngine.guiToMapConvert((int) actor.getX(), (int) actor.getY())[0] &&
                        y == GameEngine.guiToMapConvert((int) actor.getX(), (int) actor.getY())[1] &&
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
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), false);
                                } else {
                                    for (int[] ints : GameEngine.getPossibleTargets((Hero) actor, skill.getIndex())) {
                                        stage.addActor(new Highlight("highlight.png", ints[1], ints[0]));

                                    }
                                    buttonPressed.remove(skill.getIndex());
                                    buttonPressed.add(skill.getIndex(), true);
                                    makeOtherButtonsFalse(skill.getIndex());
                                    actor.remove();
                                    stage.addActor(actor);
                                }
                                STATE = 2;
                                return super.touchDown(event, x, y, pointer, button);
                            }
                        });
                        iterator++;
                    }
                    break;
                }
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
    private void makeOtherButtonsFalse(int index){
        for(int i=0;i<buttonPressed.size();i++){
            if(i!=index){
                buttonPressed.remove(i);
                buttonPressed.add(i,false);
            }
        }
    }
    private void update(){
        stage.act();
    }

}
