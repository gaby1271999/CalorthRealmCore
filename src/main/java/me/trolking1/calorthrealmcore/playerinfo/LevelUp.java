package me.trolking1.calorthrealmcore.playerinfo;

/**
 * Created by Gabriel on 4/14/2017.
 */
public class LevelUp {

    private int level, xp;

    public LevelUp(int level, int xp) {
        this.level = level;
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}