package Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.StrategicGame;

/**
 * Abstract screen class to help making more screens easier
 */
public abstract class AbstractScreen implements Screen, InputProcessor {
    protected StrategicGame game;
    /**
     * Stage is a place where we place actors (objects with images) and where they are displayed
     */

    public static Stage stage;
    /**
     * Camera dictates what is to be shown on a stage
     */
    protected OrthographicCamera camera;
    /**
     * When its opened we can "paint" on the screen
     */
    protected SpriteBatch spriteBatch;

    public AbstractScreen(StrategicGame game) throws Exception {
        this.game = game;
        createCamera();
        stage = new Stage(new FitViewport(StrategicGame.WIDTH, StrategicGame.HEIGHT, camera));
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        init();
    }


    protected abstract void init() throws Exception;

    /**
     * Operating camera
     */
    protected void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, StrategicGame.WIDTH, StrategicGame.HEIGHT);
        camera.update();
    }

    /**
     * All the calculations to update a frame
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    protected void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {

    }

    @Override
    public void resume() {
        game.setPaused(false);
    }

    @Override
    public void pause() {
        game.setPaused(true);
    }

    @Override
    public void dispose() {
        game.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {

    }
}
