package me.trolking1.calorthrealmcore.commands.admin.dungeon;

import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CreateDungeonCommand extends CommandInterface {

    public CreateDungeonCommand() {
        super(null, "admin.createdungeon.description", "admin", "a");

        Map<String, Integer> args = new HashMap<>();
        args.put("createdungeon", 0);
        args.put("cd", 0);
        setArgs(args);
        setUsage("admin", "createdungeon");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        return false;
    }
}
