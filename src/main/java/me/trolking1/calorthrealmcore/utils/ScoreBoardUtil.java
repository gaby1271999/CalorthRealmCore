package me.trolking1.calorthrealmcore.utils;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.playerinfo.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.lang.reflect.Field;

public class ScoreBoardUtil {

    public ScoreBoardUtil() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    createScoreBoard(player);
                }
            }
        }, 0, 20);
    }

    /**
     * Creates a scoreboard frame for a players stats.
     *
     * @param player
     */
    private void createScoreBoard(Player player) {
        if (!player.getMetadata("playerdata").isEmpty()) {
            PlayerData playerData = Main.getPlayerInfoManager().getPlayerData(player);
            Object playerClass = playerData.getPlayerClass();

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

            Objective objective = scoreboard.registerNewObjective("test", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(Main.getMessageManager().toColor(Main.getConfigManager().getConfig().getConfig().getString("playerinfoboard.title").replace("%player%", player.getName())));

            int counter = Main.getConfigManager().getConfig().getConfig().getStringList("playerinfoboard.rows").size();
            for (String row : Main.getConfigManager().getConfig().getConfig().getStringList("playerinfoboard.rows")) {
                Score score = objective.getScore(Main.getMessageManager().toColor(addPlaceHoldeders(player, playerData, playerClass, row)));
                score.setScore(counter);
                counter--;
            }

            player.setScoreboard(scoreboard);
        }
    }

    /**
     * Checks if a field can be used as a parameter in the scoreboard.
     *
     * @param field
     * @return true if the field can be used else false
     */
    private String isAcceptableField(Field field) {
        if (field.getName().equals("damage") || field.getName().equals("range") || field.getName().equals("defence") ||
                field.getName().equals("attackSpeed") || field.getName().equals("hunger")) {
            return "%" + field.getName() + "%";
        }

        return null;
    }

    /**
     *  returns a sting with the placeholders filled in.
     *
     * @param player
     * @param playerData
     * @param playerClass
     * @param string
     * @return Sting with the place holders.
     */
    private String addPlaceHoldeders(Player player, PlayerData playerData, Object playerClass, String string) {
        String newString = string;
        for (Field field : playerClass.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (isAcceptableField(field) != null) {
                try {
                    newString = newString.replace(isAcceptableField(field), String.valueOf(field.get(playerClass)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return newString.replace("%player%", player.getName()).replace("%lvl%", String.valueOf(playerData.getLevelUp().getLevel())).replace("%xp%", String.valueOf(playerData.getLevelUp().getXp())).replace("%class%", playerClass.getClass().getSimpleName());
    }
}
