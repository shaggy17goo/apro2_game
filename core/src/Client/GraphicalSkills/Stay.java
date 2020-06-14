package Client.GraphicalSkills;

public class Stay extends Skill {
    public Stay(int index) {
        super("skillGraphics/arrow.png");
        this.index = index;
        distance = 0;
        afterAttack = SkillProperty.GoToTarget;
        useDistance = SkillProperty.Flood;
        rangeType = SkillProperty.PointRange;
    }

    @Override
    public void useSkill(int yh, int xh, int yt, int xt) {
        //wait
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
