package Client;

import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.*;
import java.util.List;

public class Animation {
	/**
	 *
	 * @param hero Hero using skill
	 * @param skill Skill being used
	 * @param y y-coordinate of field the skill is used on
	 * @param x x-coordinate of field the skill is used on
	 */
	public static void animate(Hero hero, Skill skill, int y, int x){
		int Y = (int) hero.getY();
		int X = (int) hero.getX();
		int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
		List<int[]> dfs = GameEngine.getPointsInRangeDFS(y, x, 1);

		if (skill instanceof Walk) {
			((Walk) skill).walkTo(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof AreaHeal) {
			((AreaHeal) skill).healArea(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Arrow) {
			((Arrow) skill).fireArrow(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof ArrowVolley) {
			for(int[] ints : dfs){
				int [] coord = CorrelationUtils.mapToGuiConvert(ints[1],ints[0]);
				((ArrowVolley) skill).fireArrowVolley(Y, X, coord[1], coord[0]);
				try {
					Thread.sleep(575);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else if (skill instanceof Charge) {
			((Charge) skill).chargeAttack(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Fireball) {
			((Fireball) skill).throwFireball(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Heal) {
			((Heal) skill).healAni(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Jump) {
			((Jump) skill).jumpAni(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Melee) {
			((Melee) skill).meleeAttack(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Necromancy) {
			((Necromancy) skill).resurrect(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Stomp) {
			((Stomp) skill).tread(Y, X, coords[1], coords[0]);
		}
		else if (skill instanceof Teleport) {
			((Teleport) skill).teleportTo(Y, X, coords[1], coords[0]);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}