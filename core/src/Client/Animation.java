package Client;

import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.*;

public class Animation {
	/**
	 *
	 * @param hero Hero using skill
	 * @param skill Skill being used
	 * @param y y-coordinate of field the skill is used on
	 * @param x x-coordinate of field the skill is used on
	 */
	public static void animate(Hero hero, Skill skill, int y, int x){
		int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
		if (skill instanceof Walk) {
			((Walk) skill).walkTo((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof AreaHeal) {
			((AreaHeal) skill).healArea((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Arrow) {
			((Arrow) skill).fireArrow((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof ArrowVolley) {
			((ArrowVolley) skill).fireArrowVolley((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Charge) {
			((Charge) skill).chargeAttack((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Fireball) {
			((Fireball) skill).throwFireball((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Heal) {
			((Heal) skill).healAni((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Jump) {
			((Jump) skill).jumpAni((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Melee) {
			((Melee) skill).meleeAttack((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Necromancy) {
			((Necromancy) skill).resurrect((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Step) {
			((Step) skill).tread((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Teleport) {
			((Teleport) skill).teleportTo((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
