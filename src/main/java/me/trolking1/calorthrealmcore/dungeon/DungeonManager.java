package me.trolking1.calorthrealmcore.dungeon;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 6/4/2017.
 */
public class DungeonManager {

    private List<Dungeon> dungeons = new ArrayList<>();

    public void setupDungeons() {
        if (Main.getConfigManager().getDungeons() != null) {
            for (Dungeon dungeon : (List<Dungeon>) Main.getConfigManager().getDungeons().getConfig().get("dungeons")) {
                dungeons.add(dungeon);
            }
        }

        return;
    }

    public Dungeon getDungeon(int id) {
        for (Dungeon dungeon : dungeons) {
            if (dungeon.getId() == id) {
                return dungeon;
            }
        }

        return null;
    }

    public Dungeon getDungeo(Location location) {
        for (Dungeon dungeon : dungeons) {
            if (dungeon.getRegion().inRegion(location)) {
                return dungeon;
            }
        }

        return null;
    }

    public Dungeon getDungeon(Player player) {
        for (Dungeon dungeon : dungeons) {
            if (dungeon.containsPlayer(player)) {
                return dungeon;
            }
        }

        return null;
    }
}
