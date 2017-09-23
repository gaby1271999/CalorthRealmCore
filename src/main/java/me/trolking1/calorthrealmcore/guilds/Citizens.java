package me.trolking1.calorthrealmcore.guilds;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel on 3/30/2017.
 */
public class Citizens {

    private Player leader;
    private List<Player> officers, members, guests;

    public Citizens(String leaderUUID, List<String> officerUUID, List<String> memberUUID, List<String> guestUUID) {
        this.leader = Bukkit.getPlayer(UUID.fromString(leaderUUID));
        this.officers = convertToPlayerList(officerUUID);
        this.members = convertToPlayerList(memberUUID);
        this.guests = convertToPlayerList(guestUUID);
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public List<Player> getOfficers() {
        return officers;
    }

    public void setOfficers(List<Player> officers) {
        this.officers = officers;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public List<Player> getGuests() {
        return guests;
    }

    public void setGuests(List<Player> guests) {
        this.guests = guests;
    }

    public boolean containsPlayer(Player player) {
        if (player.getName().equals(leader.getName())) {
            return true;
        }

        for (Player officer : officers) {
            if (player.getName().equals(officer.getName())) {
                return true;
            }
        }


        for (Player member : members) {
            if (player.getName().equals(member.getName())) {
                return true;
            }
        }


        for (Player guest : guests) {
            if (player.getName().equals(guest.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean addCitizen(String guildName, Player citizen) {
        return Main.database.addCitizen(guildName, citizen, 4);
    }

    public boolean removeCitizen(String guildName, Player citizen) {
        if (getRank(citizen) != 0 && getRank(citizen) != 1) {
            return Main.database.removeCitizen(guildName, citizen, getRank(citizen));
        } else {
            return false;
        }
    }

    public int getRank(Player player) {
        if (player.getName().equals(leader.getName())) {
            return 1;
        }

        for (Player officer : officers) {
            if (player.getName().equals(officer.getName())) {
                return 2;
            }
        }


        for (Player member : members) {
            if (player.getName().equals(member.getName())) {
                return 3;
            }
        }


        for (Player guest : guests) {
            if (player.getName().equals(guest.getName())) {
                return 4;
            }
        }

        return 0;
    }

    public List<Player> getAllCitizens() {
        List<Player> citizens = new ArrayList<>();

        citizens.add(leader);
        citizens.addAll(officers);
        citizens.addAll(members);
        citizens.addAll(guests);

        return citizens;
    }

    private List<Player> convertToPlayerList(List<String> uuidList) {
        List<Player> playerList = new ArrayList<>();

        for (String uuid : uuidList) {
            playerList.add(Bukkit.getPlayer(UUID.fromString(uuid)));
        }

        return playerList;
    }
}
