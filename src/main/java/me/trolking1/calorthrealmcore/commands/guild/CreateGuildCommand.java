package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class CreateGuildCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            int event = Main.guildUtils.createGuild(player, args[1]);
            switch (event) {
                case 1:
                    Main.messageManager.sendMessageFromConfig(player, "guild.create.success");
                    return true;
                case 2:
                    Main.messageManager.sendMessageFromConfig(player, "guild.create.nospace");
                    return true;
                case 3:
                    Main.messageManager.sendMessageFromConfig(player, "guild.create.nameexists");
                    return true;
                case 4:
                    Main.messageManager.sendMessageFromConfig(player, "guild.create.nomoney");
                    return true;
                case 5:
                    Main.messageManager.sendMessageFromConfig(player, "guild.create.inotherguild");
                    return true;
            }
        } else {
            Main.messageManager.sendMessageFromConfig(player, "guild.create.commanderror");
        }

        return false;
    }
}
