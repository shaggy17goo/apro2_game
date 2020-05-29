package Client;

import Client.GraphicalHeroes.Hero;
import Client.GraphicalSkills.*;

public class Animation {
	public static void animate(Hero hero, Skill skill, int y, int x){
		if (skill instanceof Walk) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Walk) skill).walkTo((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Arrow) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Arrow) skill).fireArrow((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Fireball) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Fireball) skill).throwFireball((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Heal) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Heal) skill).healAni((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Jump) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Jump) skill).jumpAni((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Melee) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Melee) skill).meleeAttack((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Necromancy) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Necromancy) skill).resurrect((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		else if (skill instanceof Teleport) {
			int[] coords = CorrelationUtils.mapToGuiConvert(x, y);
			((Teleport) skill).teleportTo((int) hero.getY(), (int) hero.getX(), coords[1], coords[0]);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
