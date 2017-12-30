package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class CreateGuildCommand extends CommandInterface {

    public CreateGuildCommand() {
        super(null, "guild.createguild.description", "guild", "g");

        Map<String, Integer> args = new HashMap<>();
        args.put("createguild", 0);
        args.put("cg", 0);
        setArgs(args);
        setUsage("guild", "createguild", "<player>");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 2) {
            int event = Main.getGuildUtils().createGuild(player, args[1]);

            switch (event) {
                case 1:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.create.success");
                    return true;
                case 2:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.create.nospace");
                    return true;
                case 3:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.create.nameexists");
                    return true;
                case 4:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.create.nomoney");
                    return true;
                case 5:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.create.inotherguild");
                    return true;
            }
        }

        return false;
    }
}
