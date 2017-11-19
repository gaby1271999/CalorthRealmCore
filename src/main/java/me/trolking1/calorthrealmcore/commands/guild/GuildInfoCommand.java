package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/3/2017.
 */
public class GuildInfoCommand extends CommandInterface {

    public GuildInfoCommand() {
        super(null, "guild.guildinfocommand.description", "guild", "g");

        Map<String, Integer> args = new HashMap<>();
        args.put("guildinfo", 0);
        args.put("gi", 0);
        setArgs(args);
        setUsage("guild", "guildinfo");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 1) {
            //Main.menuManager.getInfoMenu().openInfoMenu(player, args[1]);
            //message
            return true;
        } else {
            return false;
        }
    }

}
