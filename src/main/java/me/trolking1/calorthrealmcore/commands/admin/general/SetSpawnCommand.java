package me.trolking1.calorthrealmcore.commands.admin.general;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class SetSpawnCommand extends CommandInterface {

    public SetSpawnCommand() {
        super(null, "admin.setspawn.description", "admin", "a");

        Map<String, Integer> args = new HashMap<>();
        args.put("setspawn", 0);
        args.put("ss", 0);
        setArgs(args);
        setUsage("admin", "setspawn");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 1) {
            Main.getConfigManager().getConfig().getConfig().set("spawn", player.getLocation());
            Main.getConfigManager().getConfig().saveConfig();
            Main.getMessageManager().sendMessageFromConfig(player, "admin.setspawn.succes");
            return true;
        }

        return false;
    }
}
