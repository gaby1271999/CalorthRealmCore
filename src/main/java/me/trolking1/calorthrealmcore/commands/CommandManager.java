package me.trolking1.calorthrealmcore.commands;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.guild.GuildInfoCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 4/3/2017.
 */
public class CommandManager implements CommandExecutor {

    private List<CommandInterface> commands = new ArrayList<>();

    public CommandManager() {
        Main.getMain().getCommand("guild").setExecutor(this);
        Main.getMain().getCommand("g").setExecutor(this);
        Main.getMain().getCommand("admin").setExecutor(this);
        Main.getMain().getCommand("a").setExecutor(this);
        Main.getMain().getCommand("clan").setExecutor(this);
        Main.getMain().getCommand("c").setExecutor(this);

        commands.add(new GuildInfoCommand());
    }

    public CommandInterface getCommand(String name) {
        return commands.stream().filter(commandInterface -> {
            for (String listName : commandInterface.getNames()) {
                if (name.equals(listName)) {
                    return true;
                }
            }

            return false;
        }).findFirst().orElse(null);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            CommandInterface commandInterface = getCommand(label);

            if (commandInterface != null) {
                if (commandInterface.checkPerms(player) && commandInterface.checkArgs(args)) {
                    if (!commandInterface.excecute(player, args)) {
                        commandInterface.sendUsage(player);
                        return true;
                    } else {
                        return true;
                    }
                } else {
                    sendInfo(player);
                    return true;
                }
            } else {
                sendInfo(player);
                return true;
            }
        }

        return true;
    }

    private void sendInfo(Player player) {
        commands.forEach((ci) -> {
            ci.sendCommandInfo(player);
        });
    }
}
