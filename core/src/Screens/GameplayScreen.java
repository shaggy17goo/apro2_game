package Screens;

import Client.Model.GameEngine;
import Client.Model.Heros.Archer;
import Client.Model.Heros.Hero;
import Client.Model.Map.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.StrategicGame;

public class GameplayScreen extends AbstractScreen{
    private Entity entity;
    private Button entityButton;
    public GameplayScreen(StrategicGame game) {
        super(game);
    }
    private GameEngine gameEngine;
    @Override
    protected void init(){
        initGameEngine();
        //initEntity();
        //initEntityButton();
    }
    private void initGameEngine(){
        gameEngine=new GameEngine(22,22);
        for (int yi = 0; yi < gameEngine.getGameMap().getMaxY(); yi++)
            for (int xi = 0; xi < gameEngine.getGameMap().getMaxX(); xi++)
                stage.addActor(gameEngine.getGameMap().getFieldAt(yi,xi));
    }
    private void initEntity(){
        entity = new Archer(1,1);
        stage.addActor(entity);
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
