package me.trolking1.calorthrealmcore.guilds;

import me.trolking1.calorthrealmcore.utils.Region;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Gabriel on 3/30/2017.
 */
public class Plot implements ConfigurationSerializable {

    private int id, price;
    private Region plot;
    private Player owner;
    private List<Player> friends;

    public Plot(int id, Region plot, int price, String ownerUUID, List<String> friends) {
        this.owner = Bukkit.getPlayer(UUID.fromString(ownerUUID));
        this.friends = convertToPlayerList(friends);
        this.plot = plot;
    }

    public Plot(Map<String, Object> map) {
        this.owner = Bukkit.getPlayer(UUID.fromString((String) map.get("owneruuid")));
        this.friends = convertToPlayerList((List<String>) map.get("friends"));
        this.plot = (Region) map.get("plot");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("owneruuid", owner.getUniqueId().toString());
        List<String> friendList = new ArrayList<>();
        for (Player friend : friends) {
            friendList.add(friend.getUniqueId().toString());
        }
        map.put("friends", friendList);
        map.put("plot", plot);

        return map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Region getPlot() {
        return plot;
    }

    public void setPlot(Region plot) {
        this.plot = plot;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<Player> getFriends() {
        return friends;
    }

    public void setFriends(List<Player> friends) {
        this.friends = friends;
    }

    private List<Player> convertToPlayerList(List<String> uuidList) {
        List<Player> playerList = new ArrayList<>();

        for (String uuid : uuidList) {
            playerList.add(Bukkit.getPlayer(UUID.fromString(uuid)));
        }

        return playerList;
    }
}
