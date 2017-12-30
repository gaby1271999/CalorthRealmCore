package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RemoveGuildCommand extends CommandInterface {

    public RemoveGuildCommand() {
        super(null, "guild.removeguild.description", "guild", "g");

        Map<String, Integer> args = new HashMap<>();
        args.put("removeguild", 0);
        args.put("rg", 0);
        setArgs(args);
        setUsage("guild", "removeguild");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
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
        }

        return false;
    }
}
