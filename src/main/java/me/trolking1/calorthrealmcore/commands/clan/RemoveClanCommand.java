package me.trolking1.calorthrealmcore.commands.clan;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class RemoveClanCommand extends CommandInterface {

    public RemoveClanCommand() {
        super(null, "clan.removeclan.description", "clan", "c");

        Map<String, Integer> args = new HashMap<>();
        args.put("removeclan", 0);
        args.put("rc", 0);
        setArgs(args);
        setUsage("clan", "removeclan");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 1) {
            int state = Main.getClanManager().removeClan(player);

            switch (state) {
                case 1:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.remove.success");
                    return true;
                case 2:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.remove.notaleader");
                    return true;
                case 3:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.remove.noclan");
                    return true;
            }
        }

        return false;
    }
}
