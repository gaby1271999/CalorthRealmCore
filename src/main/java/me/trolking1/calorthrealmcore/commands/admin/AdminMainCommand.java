package me.trolking1.calorthrealmcore.commands.admin;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 6/4/2017.
 */
public class AdminMainCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("")) {
                Main.messageManager.sendArrayMessageFromConfig(player, "admin.info");
            }
        }

        return false;
    }
}
