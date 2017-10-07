package me.trolking1.calorthrealmcore.commands.clan;

import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 6/5/2017.
 */
public class ClanRemove implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            int state = Main.clanManager.removeClan(player);

            switch (state) {
                case 1:
                    Main.messageManager.sendMessageFromConfig(player, "clan.remove.success");
                    return true;
                case 2:
                    Main.messageManager.sendMessageFromConfig(player, "clan.remove.notaleader");
                    return true;
                case 3:
                    Main.messageManager.sendMessageFromConfig(player, "clan.remove.noclan");
                    return true;
            }
        } else {
            Main.messageManager.sendMessageFromConfig(player, "clan.remove.commanderror");
        }

        return false;
    }
}
