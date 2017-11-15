package me.trolking1.calorthrealmcore.guilds;

import me.trolking1.calorthrealmcore.Config;
import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class GuildManager {

    private List<Guild> guilds = new ArrayList<>();

    public void onStartUp() {
        if (Main.getConfigManager().getGuilds() != null) {
            for (Config guildFile : Main.getConfigManager().getGuilds()) {
                guilds.add((Guild) guildFile.getConfig().get("guild"));
            }
        }

        refreshScoreBoard();
    }

    public Guild getGuild(String guildName) {
        for (Guild guild : guilds) {
            if (guild.getName().equals(guildName)) {
                return guild;
            }
        }

        return null;
    }

    public Guild getGuild(Location location) {
        for (Guild guild : guilds) {
            if (guild.inGuild(location)) {
                return guild;
            }
        }

        return null;
    }

    public Plot getPlot(Location location) {
        for (Guild guild : guilds) {
            if (guild.getPlots() != null) {
                for (Plot plot : guild.getPlots()) {
                    if (plot.getPlot().inRegion(location)) {
                        return plot;
                    }
                }
            }
        }

        return null;
    }

    public boolean guildNameExists(String guildName) {
        for (Guild guild : guilds) {
            if (guild.getName().equals(guildName)) {
                return true;
            }
        }

        return false;
    }

    public boolean isOwnerOfGuild(Player player) {
        for (Guild guild : guilds) {
            if (guild.getCitizens().getLeader().getName().equals(player.getName())) {
                return true;
            }
        }

        return false;
    }

    public Guild getGuildOfPlayer(Player player) {
        for (Guild guild : guilds) {
            if (guild.getCitizens().getLeader().getName().equals(player.getName())) {
                return guild;
            }
        }

        return null;
    }

    public boolean isInOtherGuild(Player player) {
        for (Guild guild : guilds) {
            if (guild.getCitizens().containsPlayer(player)) {
                return true;
            }
        }

        return false;
    }

    public boolean freeSpaceArroundChunk(Location location, boolean mainChunk) {
        int minimalchunksize = Main.getConfigManager().getGuild().getConfig().getInt("minimalchunkspace")
                + (mainChunk ? Main.getConfigManager().getGuild().getConfig().getInt("minimalmargine") : 0);

        List<Chunk> minimalChunks = new ArrayList<>();
        int minX = location.getBlockX() - (minimalchunksize*16);
        int minZ = location.getBlockZ() - (minimalchunksize*16);
        int maxX = location.getBlockX() + (minimalchunksize*16);
        int maxZ = location.getBlockZ() + (minimalchunksize*16);

        for (int x = minX; x <= maxX; x+=16) {
            for (int z = minZ; z <= maxZ; z+=16) {
                minimalChunks.add(location.getWorld().getChunkAt(x, z));
            }
        }

        for (Chunk minimalChunk : minimalChunks) {
            for (Guild guild : guilds) {
                if (guild.getMainChunk() == minimalChunk) {
                    return false;
                }

                if (guild.getChunks() != null) {
                    for (Chunk chunk : guild.getChunks()) {
                        if (chunk == minimalChunk) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public String chunkInformation(Chunk chunk) {
        for (Guild guild : guilds) {
            if (guild.getMainChunk() == chunk) {
                return guild.getName();
            }

            for (Chunk guildChunk : guild.getChunks()) {
                if (guildChunk == chunk) {
                    return guild.getName();
                }
            }
        }

        return "wilderness";
    }


    private HashMap<String, BossBar> oldBossBar = new HashMap<>();

    public void sendPlayerBoss(Player player) {
        if (Main.getConfigManager().getGuild().getConfig().getBoolean("bossbar.enable")) {
            if (oldBossBar.get(player.getName()) != null) {
                oldBossBar.get(player.getName()).removePlayer(player);
            }

            String state = chunkInformation(player.getLocation().getChunk());

            BossBar bossBar;
            if (state.equals("wilderness")) {
                bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getGuild().getConfig().getString("bossbar.inguildmessage").replace("%state%", state)),
                        BarColor.valueOf(Main.getConfigManager().getGuild().getConfig().getString("bossbar.barcolor")), BarStyle.valueOf(Main.getConfigManager().getGuild().getConfig().getString("bossbar.barstyle")));
            } else {
                bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getGuild().getConfig().getString("bossbar.wildernessmessage").replace("%state%", state)),
                        BarColor.valueOf(Main.getConfigManager().getGuild().getConfig().getString("bossbar.barcolor")), BarStyle.valueOf(Main.getConfigManager().getGuild().getConfig().getString("bossbar.barstyle")));

            }
            for (String barFlag : Main.getConfigManager().getGuild().getConfig().getStringList("bossbar.barflag")) {
                bossBar.addFlag(BarFlag.valueOf(barFlag));
            }
            bossBar.setProgress(1);
            bossBar.addPlayer(player);
            oldBossBar.put(player.getName(), bossBar);
        }
    }

    private List<String> createScoreBoardMap(Player player) {
        List<String> map = new ArrayList<>();

        for (String world : Main.getConfigManager().getGuild().getConfig().getStringList("guildworld")) {
            if (!player.getWorld().getName().equals(world)) {
                return null;
            }
        }

        int maxX = player.getLocation().getBlockX() + (2*16);
        int minX = player.getLocation().getBlockX() - (2*16);
        int maxZ = player.getLocation().getBlockZ() + (2*16);
        int minZ = player.getLocation().getBlockZ() - (2*16);

        for (int z = minZ; z <= maxZ; z+=16) {
            String line = "  ";
            for (int x = minX; x <= maxX; x+=16) {
                Chunk chunk = new Location(player.getWorld(), x, 1, z).getChunk();
                    if (chunk == player.getLocation().getChunk()) {
                        line += ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getGuild().getConfig().getString("scoreboard.map.characters.playerloc"));
                    } else if (chunkInformation(chunk).equals("wilderness")) {
                        line += ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getGuild().getConfig().getString("scoreboard.map.characters.wilderness"));
                    } else {
                        line += ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getGuild().getConfig().getString("scoreboard.map.characters.guild"));
                    }
            }

            map.add(line);
        }

        return map;
    }

    public void sendPlayerScoreBoard(Player player) {
        if (Main.getConfigManager().getGuild().getConfig().getBoolean("scoreboard.enable")) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective objective = scoreboard.registerNewObjective("guild", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getConfigManager().getGuild().getConfig().getString("scoreboard.displayname")));

            List<String> lines = Main.getConfigManager().getGuild().getConfig().getStringList("scoreboard.lines");
            int counter = lines.size();
            if (Main.getConfigManager().getGuild().getConfig().getBoolean("scoreboard.map.enable")) {
                counter += 5;
            }
            for (String line : lines) {
                Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', line));
                score.setScore(counter);
                counter--;
            }

            if (Main.getConfigManager().getGuild().getConfig().getBoolean("scoreboard.map.enable")) {
                List<String> mapLines = createScoreBoardMap(player);
                for (String mapLine : mapLines) {
                    Score score = objective.getScore(mapLine);
                    score.setScore(counter);
                    counter--;
                }
            }

            player.setScoreboard(scoreboard);
        }
    }

    public void refreshScoreBoard() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (String world : Main.getConfigManager().getGuild().getConfig().getStringList("guildworld")) {
                        if (player.getWorld().getName().equals(world)) {
                            sendPlayerScoreBoard(player);
                            continue;
                        }
                    }

                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                }
            }
        }, 20*Main.getConfigManager().getGuild().getConfig().getInt("scoreboard.refresh"));
    }

    public List<Guild> getGuilds() {
        return guilds;
    }
}
