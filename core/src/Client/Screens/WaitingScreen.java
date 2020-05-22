package Client.Screens;

import Client.Client;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.StrategicGame;

public class WaitingScreen extends AbstractScreen {
    private Texture splashImg;
    public static boolean readyToGame;
    public WaitingScreen(StrategicGame game) throws Exception {
        super(game);
    }

    @Override
    protected void init() throws Exception {
        splashImg = new Texture("screenGraphics/waitingScreen1.jpg");
        StrategicGame.client = new Client(game,true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        spriteBatch.begin();
        spriteBatch.draw(splashImg,StrategicGame.WIDTH/2 -splashImg.getWidth()/2,
                StrategicGame.HEIGHT/2 -splashImg.getHeight()/2);
        spriteBatch.end();
        if(readyToGame){
            try {
                game.setScreen(new GameplayScreen(game));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
