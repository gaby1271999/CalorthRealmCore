package me.trolking1.calorthrealmcore.clan;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel on 4/27/2017.
 */
public class Clan {

    private String name, clanTag;
    private Player clanLeader;
    private List<Player> members;

    public Clan(String name, String clanTag, String clanLeader, List<String> members) {
        this.name = name;
        this.clanTag = clanTag;
        this.clanLeader = Bukkit.getPlayer(UUID.fromString(clanLeader));
        List<Player> toPlayers = new ArrayList<>();
        for (String member : members) {
            toPlayers.add(Bukkit.getPlayer(UUID.fromString(member)));
        }
        this.members = toPlayers;
    }

    public boolean containsPlayer(Player player) {
        for (Player member : members) {
            if (member.getName().equals(player.getName())) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClanTag() {
        return clanTag;
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag;
    }

    public Player getClanLeader() {
        return clanLeader;
    }

    public void setClanLeader(Player clanLeader) {
        this.clanLeader = clanLeader;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }
}
