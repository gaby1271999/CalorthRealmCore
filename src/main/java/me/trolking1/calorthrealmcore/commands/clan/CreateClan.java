package me.trolking1.calorthrealmcore.commands.clan;

import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 6/5/2017.
 */
public class CreateClan implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            int state = Main.clanManager.createClan(player, args[1], args[2]);

            switch (state) {
                case 1:
                    Main.messageManager.sendMessageFromConfig(player, "clan.create.success");
                    return true;
                case 2:
                    Main.messageManager.sendMessageFromConfig(player, "clan.create.clantagexists");
                    return true;
                case 3:
                    Main.messageManager.sendMessageFromConfig(player, "clan.create.clannameexists");
                    return true;
                case 4:
                    Main.messageManager.sendMessageFromConfig(player, "clan.create.hasclan");
                    return true;
            }
        } else {
            Main.messageManager.sendMessageFromConfig(player, "clan.create.commanderror");
        }

        return false;
    }
}
