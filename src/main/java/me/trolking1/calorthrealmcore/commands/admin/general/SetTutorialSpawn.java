package me.trolking1.calorthrealmcore.commands.admin.general;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 7/3/2017.
 */
public class SetTutorialSpawn implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Main.configManager.getConfig().getConfig().set("tutorialspawn", player.getLocation());
            Main.configManager.getConfig().saveConfig();
            Main.messageManager.sendMessageFromConfig(player, "admin.settutorialspawn.succes");
            return true;
        } else {
            Main.messageManager.sendMessage(player, "admin.settutorialspawn.commanderror");
        }

        return false;
    }
}
