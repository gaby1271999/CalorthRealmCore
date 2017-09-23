package me.trolking1.calorthrealmcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class NewCommand implements CommandExecutor {

    private HashMap<String, CommandInterface> cmds = new HashMap<>();
    private String mainCommand;

    public NewCommand(String mainCommand) {
        this.mainCommand = mainCommand;
    }

    private boolean cmdExists(String name) {
        if (cmds.get(name) != null) {
            return true;
        }

        return false;
    }

    private CommandInterface getcmd(String name) {
        return cmds.get(name);
    }

    public void addCommand(String name, CommandInterface cmdInterface) {
        cmds.put(name, cmdInterface);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            if (args.length == 0) {
                getcmd(mainCommand).onCommand(commandSender, command, label, args);
                return true;
            } else {
                if (cmdExists(args[0])) {
                    getcmd(args[0]).onCommand(commandSender, command, label, args);
                    return true;
                } else {
                    //command does not exists
                    return false;
                }
            }
        }
        //You are not a player!
        return false;
    }

    public String getMainCommand() {
        return mainCommand;
    }
}
