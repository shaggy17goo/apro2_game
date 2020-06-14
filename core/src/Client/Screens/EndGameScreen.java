package Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.StrategicGame;

/**
 * Endgame screen
 */
public class EndGameScreen extends AbstractScreen {
    public EndGameScreen(StrategicGame game) throws Exception {
        super(game);
        stage = new Stage(new FitViewport(game.WIDTH, game.HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);  //set stage as a input processor
        init();

    }

    @Override
    protected void init() {
        addBackground();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        spriteBatch.begin();
        update();
        spriteBatch.end();

    }


    private void update() {
        stage.draw();
        stage.act();
    }


    private void addBackground() {
        String path;
        if (game.logicalPlayer.hasAliveHeroes())
            path = "screenGraphics/win.png";
        else
            path = "screenGraphics/lose.png";

        TextureRegion textureRegion = new TextureRegion(new Texture(path));
        final Image background = new Image(textureRegion);
        background.setSize(game.WIDTH, game.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
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
