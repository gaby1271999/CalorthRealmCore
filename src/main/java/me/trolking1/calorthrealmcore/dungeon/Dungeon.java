package me.trolking1.calorthrealmcore.dungeon;

import me.trolking1.calorthrealmcore.utils.Region;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 4/4/2017.
 */
public class Dungeon implements ConfigurationSerializable {

    private byte maxAmountPlayers;
    private int id, minlLevel, maxLevel;
    private Region region;
    private boolean full;
    private List<Player> players;

    public Dungeon(int id, byte maxAmountPlayers, int minlLevel, int maxLevel, Region region) {
        this.id = id;
        this.maxAmountPlayers = maxAmountPlayers;
        this.minlLevel = minlLevel;
        this.maxLevel = maxLevel;
        this.region = region;
    }

    public Dungeon(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.maxAmountPlayers = (byte) map.get("maxamountplayers");
        this.minlLevel = (int) map.get("minlevel");
        this.maxLevel = (int) map.get("maxlevel");
        this.region = (Region) map.get("region");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();


        return null;
    }

    public boolean containsPlayer(Player player) {
        if (player != null) {
            for (Player playerInList : players) {
                if (playerInList.getName().equals(player.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public byte getMaxAmountPlayers() {
        return maxAmountPlayers;
    }

    public void setMaxAmountPlayers(byte maxAmountPlayers) {
        this.maxAmountPlayers = maxAmountPlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinlLevel() {
        return minlLevel;
    }

    public void setMinlLevel(int minlLevel) {
        this.minlLevel = minlLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
