package me.trolking1.calorthrealmcore.guilds;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.utils.Region;
import org.bukkit.block.Block;

/**
 * Created by Gabriel on 4/1/2017.
 */
public class GuildLvl {

    private int lvl;

    public GuildLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getGuildLvl(Region region) {
        double lvl = 0;

        for (Block block : region.getRegionBlocks()) {
            for (String item : Main.getConfigManager().getConfig().getConfig().getStringList("pointsperblock")) {
                String[] type = item.split("-");
                String[] idAndData = type[0].split(":");
                if (Integer.valueOf(idAndData[1]) == 0) {
                    if (block.getType().getId() == Integer.valueOf(idAndData[0])) {
                        lvl += Double.valueOf(type[1]);
                    }
                } else {
                    if (block.getType().getId() == Integer.valueOf(idAndData[0]) && block.getData() == Integer.valueOf(idAndData[1])) {
                        lvl += Double.valueOf(type[1]);
                    }
                }
            }
        }

        return (int) lvl;
    }
}
