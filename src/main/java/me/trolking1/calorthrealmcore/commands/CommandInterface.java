package me.trolking1.calorthrealmcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Gabriel on 3/29/2017.
 */
public interface CommandInterface {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args);

}
