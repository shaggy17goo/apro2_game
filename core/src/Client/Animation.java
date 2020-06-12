package Client;

import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.Skill;

public class Animation {
    /**
     * @param hero  Hero using skill
     * @param skill Skill being used
     * @param y     y-coordinate of field the skill is used on
     * @param x     x-coordinate of field the skill is used on
     */
    public static void animate(Hero hero, Skill skill, int y, int x) {
        int Y = (int) hero.getY();
        int X = (int) hero.getX();
        int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
        skill.useSkill(Y, X, coords[1], coords[0]);
        /*try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

}