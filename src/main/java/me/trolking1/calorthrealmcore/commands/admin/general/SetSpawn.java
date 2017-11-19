package me.trolking1.calorthrealmcore.commands.admin.general;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 6/28/2017.
 */
/*
public class SetSpawn implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Main.getConfigManager().getConfig().getConfig().set("spawn", player.getLocation());
            Main.getConfigManager().getConfig().saveConfig();
            Main.getMessageManager().sendMessageFromConfig(player, "admin.setspawn.succes");
            return true;
        } else {
            Main.getMessageManager().sendMessage(player, "admin.setspawn.commanderror");
        }

        return false;
    }
}*/
