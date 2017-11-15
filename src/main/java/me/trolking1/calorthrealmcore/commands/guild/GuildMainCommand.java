package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class GuildMainCommand implements CommandInterface {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        Main.getMessageManager().sendArrayMessageFromConfig(player, "guild.info");

        return false;
    }
}
