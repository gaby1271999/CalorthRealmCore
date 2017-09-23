package me.trolking1.calorthrealmcore.clan;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 4/27/2017.
 */
public class ClanManager {

    private List<Clan> clans = new ArrayList<>();

    public ClanManager() {

    }

    public Clan getClanOfPlayer(Player player) {
        for (Clan clan : clans) {
            if (clan.containsPlayer(player)) {
                return clan;
            }

            if (clan.getClanLeader().getName().equals(player.getName())) {
                return clan;
            }
        }

        return null;
    }

    public int createClan(Player player, String name, String tag) {
        if (getClanOfPlayer(player) != null) {
            return 4;
        }

        if (clanNameExists(name)) {
            return 3;
        }

        if (clanTagExists(tag)) {
            return 2;
        }

        Clan clan = new Clan(name, tag, player.getName(), null);

        clans.add(clan);

        return 1;
    }

    public int removeClan(Player player) {
        if (getClanOfPlayer(player) == null) {
            return 3;
        }

        Clan clan = getClanOfPlayer(player);

        if (!clan.getClanLeader().equals(player.getName())) {
            return 2;
        }

        clans.remove(clan);
        return 1;
    }

    public boolean clanNameExists(String name) {
        if (clans != null) {
            for (Clan clan : clans) {
                if (clan.getName().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean clanTagExists(String tag) {
        if (clans != null) {
            for (Clan clan : clans) {
                if (clan.getClanTag().equals(tag)) {
                    return true;
                }
            }
        }

        return false;
    }
}
