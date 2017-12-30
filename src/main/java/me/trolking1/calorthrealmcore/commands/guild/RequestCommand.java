package me.trolking1.calorthrealmcore.commands.guild;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RequestCommand extends CommandInterface {

    public RequestCommand() {
        super(null, "guild.request.description", "guild", "g");

        Map<String, Integer> args = new HashMap<>();
        args.put("request", 0);
        args.put("r", 0);
        setArgs(args);
        setUsage("guild", "request", "<player>");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("accept")) {
                if (Main.getGuildUtils().request.get(player.getName()) != null) {
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.request.accept.success");
                    Main.getMessageManager().sendMessage(Bukkit.getPlayer(Main.getGuildUtils().invitor.get(player.getName())), Main.getMessageManager().getMessageFromConfig("guild.request.accept.successtoinviter").replace("%player%", player.getName()));
                    Main.getGuildUtils().addCitizen(player, true);
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("deny")) {
                if (Main.getGuildUtils().request.get(player.getName()) != null) {
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.request.deny.success");
                    Main.getMessageManager().sendMessage(Bukkit.getPlayer(Main.getGuildUtils().invitor.get(player.getName())), Main.getMessageManager().getMessageFromConfig("guild.request.deny.denytoinviter").replace("%player%", player.getName()));
                    Main.getGuildUtils().addCitizen(player, false);
                    return true;
                }
            }

            Main.getMessageManager().sendMessageFromConfig(player, "guild.request.norequest");
            return true;
        }

        return false;
    }
}
