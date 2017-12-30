package me.trolking1.calorthrealmcore.commands.clan;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class CreateClanCommand extends CommandInterface {

    public CreateClanCommand() {
        super(null, "clan.createclan.description", "clan", "c");

        Map<String, Integer> args = new HashMap<>();
        args.put("createclan", 0);
        args.put("cc", 0);
        setArgs(args);
        setUsage("clan", "createclan", "<name>", "<clan tag>");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 2) {
            int state = Main.getClanManager().createClan(player, args[1], args[2]);

            switch (state) {
                case 1:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.create.success");
                    return true;
                case 2:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.create.clantagexists");
                    return true;
                case 3:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.create.clannameexists");
                    return true;
                case 4:
                    Main.getMessageManager().sendMessageFromConfig(player, "clan.create.hasclan");
                    return true;
            }
        }

        return false;
    }
}
