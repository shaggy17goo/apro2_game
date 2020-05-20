package Client.Screens;

import Client.Client;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.StrategicGame;
import org.w3c.dom.Text;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;

public class ConnectingScreen extends AbstractScreen {
    private TextField ipField;
    private TextField portField;
    private TextField nickField;


    public ConnectingScreen(StrategicGame game) throws Exception {
        super(game);
        stage = new Stage(new FitViewport(game.WIDTH, game.HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);  //set stage as a input processor
        init();

    }

    @Override
    protected void init() {
        addBackground();
        ipInput();
        nickInput();
        portInput();
        //buttons
        chooseHeroes();
        nextScreenButton();
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


    private void nextScreenButton() {
        TextButton button = new TextButton("Join new game", game.skin);
        button.setSize(250, 50);
        button.setPosition(700, 20);
        button.setDebug(true);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    game.ip = ipField.getText();
                    game.nick = nickField.getText();
                    Player player = new Player(game.nick);
                    game.player=player;
                    game.port = portField.getText();
                    if (!ipField.getText().equals("") && !portField.getText().equals("") && !nickField.getText().equals("") && chooseHeroes()) {
                        game.setScreen(new GameplayScreen(game));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }


    private void addBackground() {
        TextureRegion textureRegion = new TextureRegion(new Texture("background.png"));
        final Image background = new Image(textureRegion);
        background.setSize(game.WIDTH, game.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }


    private void ipInput() {
        ipField = new TextField("", game.skin);
        ipField.setMessageText("IP");
        ipField.setPosition(50, 50);
        ipField.setSize(200, 40);
        ipField.setDebug(true);
        stage.addActor(ipField);
    }

    private void portInput() {
        portField = new TextField("", game.skin);
        portField.setMessageText("Port");
        portField.setPosition(50, 150);
        portField.setSize(200, 40);
        portField.setDebug(true);
        stage.addActor(portField);
    }

    private void nickInput() {
        nickField = new TextField("", game.skin);
        nickField.setMessageText("Nick");
        nickField.setPosition(50, 250);
        nickField.setSize(200, 40);
        nickField.setDebug(true);
        stage.addActor(nickField);
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


    public boolean chooseHeroes() {
        ArrayList<TextButton> heroesButtonList = new ArrayList<>();
        heroesButtonList.add(new TextButton("Archer", game.skin));
        heroesButtonList.add(new TextButton("Necro", game.skin));
        heroesButtonList.add(new TextButton("Paladin", game.skin));
        heroesButtonList.add(new TextButton("Priest", game.skin));
        heroesButtonList.add(new TextButton("Warrior", game.skin));
        heroesButtonList.add(new TextButton("Wizard", game.skin));


        for (int i = 0; i < 6; i++) {
            heroesButtonList.get(i).setSize(160, 50);
            heroesButtonList.get(i).setPosition(i * 160 + 80, 600);
            heroesButtonList.get(i).setDebug(true);
            final int finalI = i;
            heroesButtonList.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.choseHeroes[finalI] = !game.choseHeroes[finalI];
                    showChosenHeroes();

                }
            });
            stage.addActor(heroesButtonList.get(i));
        }

        int heroesCnt=0;
        for (int i = 0; i < 6; i++) {
            if(game.choseHeroes[i])
                heroesCnt++;
        }
        if (heroesCnt==4)
            return true;
        else
            return false;

    }


    public void showChosenHeroes() {
        StringBuilder chosenString = new StringBuilder();
        for (int i = 0; i < game.choseHeroes.length; i++) {
            if (game.choseHeroes[i]) {
                switch (i){
                    case 0:{
                        chosenString.append("Archer \n");
                        break;
                    }
                    case 1:{
                        chosenString.append("Necroman \n");
                        break;
                    }
                    case 2:{
                        chosenString.append("Paladin \n");
                        break;
                    }
                    case 3:{
                        chosenString.append("Priest \n");
                        break;
                    }
                    case 4:{
                        chosenString.append("Warrior \n");
                        break;
                    }
                    case 5:{
                        chosenString.append("Wizard \n");
                        break;
                    }
                }
            }
        }

        TextArea chosenArea = new TextArea(chosenString.toString(), game.skin);
        chosenArea.setPosition(400,200);
        chosenArea.setDisabled(true);
        chosenArea.setSize(200,200);
        stage.addActor(chosenArea);
    }

}
