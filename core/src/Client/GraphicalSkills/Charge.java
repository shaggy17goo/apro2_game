package Client.GraphicalSkills;

import Client.Screens.GameplayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Charge extends Skill {
    public Charge(int value, int index) {
        super("skillGraphics/charge.png");
        this.index = index;
        distance = 12;
        this.value = value;
        range = 0;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }

    @Override
    public void useSkill(int yh, int xh, int yt, int xt) {
        GameplayScreen.stage.addActor(this);
        this.setX(xh);
        this.setY(yh);
        this.setRotation(0);
        Sound chargeSound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/charge.wav"));
        chargeSound.play();
        this.addAction(Actions.sequence(
                Actions.moveTo(xt, yt, 0.5f),
                Actions.removeActor()
        ));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
