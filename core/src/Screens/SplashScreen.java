package Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.StrategicGame;

public class SplashScreen extends AbstractScreen{
    private Texture splashImg;

    public SplashScreen(final StrategicGame game){
        super(game);
        init();

        Timer.schedule(new Timer.Task(){
            @Override
            public void run(){
                game.setScreen(new GameplayScreen(game));
            }
        },3);
    }
    @Override
    protected void init(){
        // TODO implement better assetd loading when game grows
        splashImg = new Texture("LOGO.png");
        //splashImg.
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(splashImg,StrategicGame.WIDTH/2 -splashImg.getWidth()/2,
                StrategicGame.HEIGHT/2 -splashImg.getHeight()/2);
        spriteBatch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
