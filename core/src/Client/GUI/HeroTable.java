package Client.GUI;

import Client.Model.*;
import Client.Model.graphicalheros.*;

public class HeroTable {
    /**
     * There are only 4 player and 6 heroes.
     * For simlicity we can keep them all in a fixed-index array
     * @author mpanka
     */
    private Player[] players = new Player[4];
    private Hero[][] heroTable = new Hero[4][6];

    public HeroTable() {
        for (int p = 0; p < 4; ++p) {
            for (int h = 0; h < 6; ++h) {
                heroTable[p][h] = new Archer();
            }
        }
    }

    public Player getPlayer(int i) {
        if (i < 0 || i > 3) {
            System.err.println("[HeroTable]: bad player index: " + i);
            return null;
        }
        return players[i];
    }

    public Hero getHero(int playerNumber, int heroNumber) {
        if (playerNumber < 0 || playerNumber > 3 || heroNumber < 0 || heroNumber > 5) {
            System.err.println("[HeroTable]: bad index: player: " + playerNumber + ", hero: " + heroNumber);
            return null;
        }
        return heroTable[playerNumber][heroNumber];
    }

    public void setHero(int playerNumber, int heroNumber, Hero h) {
        if (playerNumber < 0 || playerNumber > 3 || heroNumber < 0 || heroNumber > 5) {
            System.err.println("[HeroTable]: bad index: player: " + playerNumber + ", hero: " + heroNumber);
            return;
        }
        heroTable[playerNumber][heroNumber] = h;    // if h==null that's ok - we delete the hero
    }
}
