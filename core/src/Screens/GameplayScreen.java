package Screens;

import Client.Model.GameEngine;
import Client.Model.Heros.*;
import Client.Model.Player;
import Client.Model.Skills.Skill;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.StrategicGame;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends AbstractScreen{
    private Hero entity;
    //private Button entityButton;
    //private Stage backgroundStage;
    public GameplayScreen(StrategicGame game) {
        super(game);
    }
    private GameEngine gameEngine;
    @Override
    protected void init(){
        initEntity();
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
            //Archer archer=new Archer(5,5);
            player.addHero(entity);

            //player.addHero(pall);
            //player.addHero(wizz);
            //player.addHero(necc);
        List<Hero> heros=new ArrayList<>();
        gameEngine=new GameEngine(22,22);
        GameEngine.addHero(entity);
        for (int yi = 0; yi < GameEngine.getGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < GameEngine.getGameMap().getMaxX(); xi++){
                stage.addActor(GameEngine.getGameMap().getFieldAt(yi,xi));
                if(GameEngine.getGameMap().getFieldAt(yi,xi).getHero()!=null){
                    heros.add(GameEngine.getGameMap().getFieldAt(yi,xi).getHero());
                }

            }
        for (Hero hero:heros) {
            stage.addActor(hero);
        }


    }
    private void initEntity(){
        entity = new Archer(5,5);
        //stage.addActor(entity);
    }
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
        /*if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            int x=(Gdx.input.getX()-10)/32;
            int y=(Gdx.input.getY()-10)/32;
            System.out.println(x+ " "+ y);
            if(x>=22) x=21;
            if(y>=22) y=21;
            if(x<0) x=0;
            if(y<0) y=0;
            entity.reactOnClick(x,y);
        }*/
        rightClickMenu();
        update();
        spriteBatch.begin();

        stage.draw();
        //entity.draw(spriteBatch,1);
        spriteBatch.end();
    }
    private void rightClickMenu(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            List<Skill> skillList;
            int x=(Gdx.input.getX()-10)/32;
            int y=(Gdx.input.getY()-10)/32;
            for(Actor actor:stage.getActors()){

                if(x == guiToMapConvert((int)actor.getX(),(int)actor.getY())[0] &&
                        y == guiToMapConvert((int)actor.getX(),(int)actor.getY())[1] &&
                        actor.getClass().getSuperclass().equals(Hero.class)){
                    System.out.println("Menu");
                    skillList=GameEngine.getPossibleSkills((Hero)actor);
                    List<Button> buttonList = new ArrayList<>();
                    int iterator=0;
                    for(Skill skill:skillList){
                        buttonList.add(new Button(new Button.ButtonStyle()));
                        buttonList.get(iterator).setWidth(128);
                        buttonList.get(iterator).setHeight(64);
                        buttonList.get(iterator).setX(StrategicGame.CONTROLPANELX);
                        buttonList.get(iterator).setY(20+iterator*(64+5));
                        buttonList.get(iterator).setDebug(true);//TODO false in this place
                        stage.addActor(buttonList.get(iterator));
                        buttonList.get(iterator).addListener(new ClickListener(){
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                //entity.reactOnClick();
                                System.out.println("Działam");
                                return super.touchDown(event, x, y, pointer, button);
                            }
                        });
                        iterator++;
                    }
                    break;

                }
            }
            //entity.reactOnClick(x,y);
        }
    }

    /**
     * Translate Actor's coordinates to map coordinates
     */
    private int[] guiToMapConvert(int x,int y){
        return new int[]{(x-10)/32,(StrategicGame.HEIGHT-y-32-10)/32};
    }
    private void update(){
        stage.act();
        //System.out.println("Update");
    }

}
