package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 4/30/2017.
 */
public class RequestCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("accept")) {
                if (Main.getGuildUtils().request.get(player.getName()) != null) {
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.request.accept.success");
                    Main.getMessageManager().sendMessage(Bukkit.getPlayer(Main.getGuildUtils().invitor.get(player.getName())), Main.getMessageManager().getMessageFromConfig("guild.request.accept.successtoinviter").replace("%player%", player.getName()));
                    Main.getGuildUtils().addCitizen(player, true);
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("deny")) {
                if (Main.getGuildUtils().request.get(player.getName()) != null) {
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.request.deny.success");
                    Main.getMessageManager().sendMessage(Bukkit.getPlayer(Main.getGuildUtils().invitor.get(player.getName())), Main.getMessageManager().getMessageFromConfig("guild.request.deny.denytoinviter").replace("%player%", player.getName()));
                    Main.getGuildUtils().addCitizen(player, false);
                    return true;
                }
            }

            Main.getMessageManager().sendMessageFromConfig(player, "guild.request.norequest");
            return true;
        }

        Main.getMessageManager().sendMessageFromConfig(player, "guild.request.commanderror");
        return false;
    }
}
