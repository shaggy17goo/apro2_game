package Screens;

import Client.Model.GameEngine;
import Client.Model.Heros.*;
import Client.Model.Map.Entity;
import Client.Model.Player;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.StrategicGame;

public class GameplayScreen extends AbstractScreen{
    private Hero entity;
    private Button entityButton;
    public GameplayScreen(StrategicGame game) {
        super(game);
    }
    private GameEngine gameEngine;
    @Override
    protected void init(){
        initEntity();
        initGameEngine();
        initEntityButton();
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

        gameEngine=new GameEngine(22,22);
        gameEngine.addHero(entity);
        for (int yi = 0; yi < gameEngine.getGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < gameEngine.getGameMap().getMaxX(); xi++){
                stage.addActor(gameEngine.getGameMap().getFieldAt(yi,xi));
                if(gameEngine.getGameMap().getFieldAt(yi,xi).getHero()!=null)
                    stage.addActor(gameEngine.getGameMap().getFieldAt(yi,xi).getHero());
            }

    }
    private void initEntity(){
        entity = new Archer(5,5);
        //stage.addActor(entity);
    }
    private void initEntityButton(){
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
    }
    //Calculate (render) all moves
    @Override
    public void render(float delta){
        //gameEngine.useSkill()
        super.render(delta);

        update();

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }
    private void update(){
        stage.act();
    }

}
