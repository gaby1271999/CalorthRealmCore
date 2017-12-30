package me.trolking1.calorthrealmcore.commands;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.admin.dungeon.CreateDungeonCommand;
import me.trolking1.calorthrealmcore.commands.admin.general.SetSpawnCommand;
import me.trolking1.calorthrealmcore.commands.admin.general.SetTutorialSpawnCommand;
import me.trolking1.calorthrealmcore.commands.admin.mobspawnregions.MobRegionCommand;
import me.trolking1.calorthrealmcore.commands.clan.CreateClanCommand;
import me.trolking1.calorthrealmcore.commands.guild.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        //guild commands
        commands.add(new AddCitizenCommand());
        commands.add(new CreateGuildCommand());
        commands.add(new GuildInfoCommand());
        commands.add(new RemoveCitizenCommand());
        commands.add(new RemoveGuildCommand());
        commands.add(new RequestCommand());

        //clan commands
        commands.add(new CreateClanCommand());
        commands.add(new RemoveGuildCommand());

        //admin commands
        commands.add(new CreateDungeonCommand());
        commands.add(new SetSpawnCommand());
        commands.add(new SetTutorialSpawnCommand());
        commands.add(new MobRegionCommand());
    }


    //TODO send array of commands that contains those main command name.
    public List<CommandInterface> getCommand(String name) {
        List<CommandInterface> commandInterfaces = new ArrayList<>();

        commandInterfaces = commands.stream().filter(commandInterface -> commandInterface.containsName(name)).collect(Collectors.toList());

        return commandInterfaces;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            List<CommandInterface> commandInterfaces = getCommand(label);

            for (CommandInterface commandInterface : commandInterfaces) {
                if (commandInterface.checkPerms(player) && commandInterface.checkArgs(args)) {
                    if (!commandInterface.excecute(player, args)) {
                        commandInterface.sendUsage(player);

                        return true;
                    } else {
                        return true;
                    }
                }
            }

            sendInfo(commandInterfaces, player);
        }

        return true;
    }

    private void sendInfo(List<CommandInterface> commandInterfaces, Player player) {
        commandInterfaces.forEach((ci) -> {
            ci.sendCommandInfo(player);
        });
    }
}
