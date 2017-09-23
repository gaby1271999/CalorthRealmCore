package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 4/26/2017.
 */
public class AddCitizen implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 2) {
            Player citizen = Bukkit.getPlayer(args[1]);
            if (citizen == null) {
                Main.messageManager.sendMessageFromConfig(player, "guild.addcitizen.invalidplayer");
                return false;
            }

            int event = Main.guildUtils.addCitizenRequest(player, citizen);

            switch (event) {
                case 1:
                    Main.messageManager.sendMessageFromConfig(player, "guild.addcitizen.success");
                    Main.messageManager.sendMessage(citizen, Main.messageManager.getMessageFromConfig("guild.addcitizen.request").replace("%player%", player.getName()));
                    Main.messageManager.sendMessageFromConfig(citizen, "guild.addcitizen.requestinstruction");
                    return true;
                case 2:
                    Main.messageManager.sendMessageFromConfig(player, "guild.addcitizen.inotherguildcitizen");
                    return true;
                case 3:
                    Main.messageManager.sendMessageFromConfig(player, "guild.addcitizen.alreadycitizen");
                    return true;
                case 4:
                    Main.messageManager.sendMessageFromConfig(player, "guild.addcitizen.noguild");
                    return true;
            }
        } else {
            Main.messageManager.sendMessageFromConfig(player, "guild.addcitizen.commanderror");
        }

        return false;
    }
}
