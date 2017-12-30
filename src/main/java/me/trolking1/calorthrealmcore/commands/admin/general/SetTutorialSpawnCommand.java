package me.trolking1.calorthrealmcore.commands.admin.general;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SetTutorialSpawnCommand extends CommandInterface {

    public SetTutorialSpawnCommand() {
        super(null, "admin.settutorialspawn.description", "admin", "a");

        Map<String, Integer> args = new HashMap<>();
        args.put("settutorialspawn", 0);
        args.put("sts", 0);
        setArgs(args);
        setUsage("admin", "settutorialspawn");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 1) {
            Main.getConfigManager().getConfig().getConfig().set("tutorialspawn", player.getLocation());
            Main.getConfigManager().getConfig().saveConfig();
            Main.getMessageManager().sendMessageFromConfig(player, "admin.settutorialspawn.succes");
            return true;
        }

        return false;
    }
}
