package Client.Screens;

import Client.CorrelationUtils;
import Client.GraphicalHeroes.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.StrategicGame;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen for picking your heroes and initiating connection to server
 */
public class ConnectingScreen extends AbstractScreen {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    private TextField ipField;
    private TextField portField;
    private TextField nickField;
    private TextField passwordField;
    private List<Hero> stats;
    private static Hero displayedHero;
    private static boolean displayed;
    private static int lastClicked;


    public ConnectingScreen(StrategicGame game) throws Exception {
        super(game);
        stage = new Stage(new FitViewport(game.WIDTH, game.HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);  //set stage as a input processor
        init();

    }

    @Override
    protected void init() {
        addBackground();
        nickInput();
        passwordInput();
        ipInput();
        portInput();
        chooseHeroes();
        nextScreenButton();
        reconnectButton();
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
        button.setSize(300, 50);
        button.setPosition(700, 20);
        button.setDebug(false);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {//Commented for now not to force port and ip input
                    if (/*!ipField.getText().equals("") && !portField.getText().equals("") && !passwordField.getText().equals("")*/
                            !nickField.getText().equals("") && chooseHeroes()) {
                        game.nick = nickField.getText();
                        game.passHash = md.digest("password".getBytes()); //md.digest(passwordField.getText().getBytes());
                        game.ip = "127.0.0.1";//ipField.getText();
                        game.port = "1701";//portField.getText();
                        game.createPlayer();
                        game.setScreen(new WaitingScreen(game));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }


    private void reconnectButton() {
        TextButton button = new TextButton("Reconnect", game.skin);
        button.setSize(250, 50);
        button.setPosition(450, 20);
        button.setDebug(false);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {//Commented for now not to force port and ip input
                    if (/*!ipField.getText().equals("") && !portField.getText().equals("") && !passwordField.getText().equals("")*/
                            !nickField.getText().equals("")) {
                        game.nick = nickField.getText();
                        game.passHash = md.digest("password".getBytes()); //md.digest(passwordField.getText().getBytes());
                        game.ip = "127.0.0.1";//ipField.getText();
                        game.port = "1701";//portField.getText();
                        game.createPlayer();
                        game.setScreen(new WaitingScreen(game));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }


    private void addBackground() {
        TextureRegion textureRegion = new TextureRegion(new Texture("screenGraphics/backgroundCustom.png"));
        final Image background = new Image(textureRegion);
        background.setSize(game.WIDTH, game.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }


    private void ipInput() {
        ipField = new TextField("", game.skin);
        ipField.setMessageText("IP");
        ipField.setPosition(50, 150);
        ipField.setSize(200, 40);
        ipField.setDebug(false);
        ipField.setDisabled(true);
        stage.addActor(ipField);
    }

    private void portInput() {
        portField = new TextField("", game.skin);
        portField.setMessageText("Port");
        portField.setPosition(50, 50);
        portField.setSize(200, 40);
        portField.setDebug(false);
        portField.setDisabled(true);
        stage.addActor(portField);
    }

    private void nickInput() {
        nickField = new TextField("", game.skin);
        nickField.setMessageText("Nick");
        nickField.setPosition(50, 350);
        nickField.setSize(200, 40);
        nickField.setDebug(false);
        stage.addActor(nickField);
    }

    private void passwordInput() {
        passwordField = new TextField("", game.skin);
        passwordField.setMessageText("Password");
        passwordField.setPosition(50, 250);
        passwordField.setSize(200, 40);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setDebug(false);
        passwordField.setDisabled(false);
        stage.addActor(passwordField);
    }


    public boolean chooseHeroes() {
        List<TextButton> heroesButtonList = new ArrayList<>();
        stats = new ArrayList<>();
        heroesButtonList.add(new TextButton("Archer", game.skin));
        stats.add(new Client.GraphicalHeroes.Archer(0, 0));
        heroesButtonList.add(new TextButton("Necro", game.skin));
        stats.add(new Client.GraphicalHeroes.Necromancer(0, 0));
        heroesButtonList.add(new TextButton("Paladin", game.skin));
        stats.add(new Client.GraphicalHeroes.Paladin(0, 0));
        heroesButtonList.add(new TextButton("Priest", game.skin));
        stats.add(new Client.GraphicalHeroes.Priest(0, 0));
        heroesButtonList.add(new TextButton("Warrior", game.skin));
        stats.add(new Client.GraphicalHeroes.Warrior(0, 0));
        heroesButtonList.add(new TextButton("Wizard", game.skin));
        stats.add(new Client.GraphicalHeroes.Wizard(0, 0));
        heroesButtonList.add(new TextButton("Angel", game.skin));
        stats.add(new Client.GraphicalHeroes.Angel(0, 0));
        heroesButtonList.add(new TextButton("Uszatek", game.skin));
        stats.add(new Client.GraphicalHeroes.Uszatek(0, 0));

        int y, x;
        for (int i = 0; i < heroesButtonList.size(); i++) {
            if (i < 6) {
                x = i * 160 + 80;
                y = 600;
            } else {
                x = (i - 6) * 160 + 80;
                y = 510;
            }
            heroesButtonList.get(i).setSize(160, 50);
            heroesButtonList.get(i).setPosition(x, y);
            heroesButtonList.get(i).setDebug(false);
            final int finalI = i;
            heroesButtonList.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.choseHeroes[finalI] = !game.choseHeroes[finalI];
                    if (displayed || !game.choseHeroes[finalI]) {
                        displayedHero.remove();
                        displayed = false;
                    }
                    if (game.choseHeroes[finalI] || lastClicked != finalI) {
                        displayedHero = stats.get(finalI);
                        displayedHero.setPosition(740, 250);
                        displayedHero.setScale(5.0f);
                        stage.addActor(displayedHero);
                        displayed = true;

                    }
                    lastClicked = finalI;
                    if (!game.choseHeroes[finalI]) {
                        displayedHero.remove();
                        displayed = false;
                    }
                    clearTextAreas();
                    showChosenHeroes();
                    makeStatsArea(stats.get(finalI));

                }
            });
            stage.addActor(heroesButtonList.get(i));
        }

        int heroesCnt = 0;
        for (int i = 0; i < heroesButtonList.size(); i++) {
            if (game.choseHeroes[i])
                heroesCnt++;
        }
        return heroesCnt == 4;

    }

    private void makeStatsArea(Hero hero) {
        List<String> list = CorrelationUtils.makeLogicalHeroFromGraphical(hero, null).getStats();
        StringBuilder strBuilder = new StringBuilder();
        for (String str : list) {
            strBuilder.append(str + "\n");
        }
        TextArea chosenArea = new TextArea(strBuilder.toString(), new Skin(Gdx.files.internal("skin/default/skin/uiskin.json")));
        chosenArea.setPosition(860, 150);
        chosenArea.setDisabled(true);
        chosenArea.setSize(150, 250);
        stage.addActor(chosenArea);
    }

    private void clearTextAreas() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextArea) {
                stage.getActors().get(i).remove();
                i--;
            }
        }
    }


    public void showChosenHeroes() {
        StringBuilder chosenString = new StringBuilder();
        for (int i = 0; i < game.choseHeroes.length; i++) {
            if (game.choseHeroes[i]) {
                switch (i) {
                    case 0: {
                        chosenString.append("Archer \n");
                        break;
                    }
                    case 1: {
                        chosenString.append("Necroman \n");
                        break;
                    }
                    case 2: {
                        chosenString.append("Paladin \n");
                        break;
                    }
                    case 3: {
                        chosenString.append("Priest \n");
                        break;
                    }
                    case 4: {
                        chosenString.append("Warrior \n");
                        break;
                    }
                    case 5: {
                        chosenString.append("Wizard \n");
                        break;
                    }
                    case 6: {
                        chosenString.append("Angel \n");
                        break;
                    }
                    case 7: {
                        chosenString.append("Uszatek \n");
                        break;
                    }
                }
            }
        }

        TextArea chosenArea = new TextArea(chosenString.toString(), game.skin);
        chosenArea.setPosition(450, 180);
        chosenArea.setDisabled(true);
        chosenArea.setSize(200, 220);
        stage.addActor(chosenArea);
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
