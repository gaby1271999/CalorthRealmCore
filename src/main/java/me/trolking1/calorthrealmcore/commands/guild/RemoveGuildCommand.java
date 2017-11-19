package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 3/29/2017.
 */
/*
public class RemoveGuildCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            int event = Main.getGuildUtils().removeGuild(player);

            switch (event) {
                case 1:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.remove.success");
                    return true;
                case 2:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.remove.noguild");
                    return true;
            }
        } else {
            Main.getMessageManager().sendMessageFromConfig(player, "guild.remove.commanderror");
        }

        return false;
    }
}*/
