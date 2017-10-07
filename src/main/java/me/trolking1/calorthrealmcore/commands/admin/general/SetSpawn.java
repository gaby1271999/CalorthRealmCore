package me.trolking1.calorthrealmcore.commands.admin.general;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 6/28/2017.
 */
public class SetSpawn implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Main.configManager.getConfig().getConfig().set("spawn", player.getLocation());
            Main.configManager.getConfig().saveConfig();
            Main.messageManager.sendMessageFromConfig(player, "admin.setspawn.succes");
            return true;
        } else {
            Main.messageManager.sendMessage(player, "admin.setspawn.commanderror");
        }

        return false;
    }
}
