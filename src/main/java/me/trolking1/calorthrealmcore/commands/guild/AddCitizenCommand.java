package me.trolking1.calorthrealmcore.commands.guild;


import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AddCitizenCommand extends CommandInterface {

    public AddCitizenCommand() {
        super(null, "guild.addcitizen.description", "guild", "g");

        Map<String, Integer> args = new HashMap<>();
        args.put("addcitizen", 0);
        args.put("ac", 0);
        setArgs(args);
        setUsage("guild", "addcitizen", "<player>");
    }

    @Override
    public boolean excecute(Player player, String[] args) {
        if (args.length == 2) {
            Player citizen = Bukkit.getPlayer(args[1]);
            if (citizen == null) {
                Main.getMessageManager().sendMessageFromConfig(player, "guild.addcitizen.invalidplayer");
                return true;
            }

            int event = Main.getGuildUtils().addCitizenRequest(player, citizen);

            switch (event) {
                case 1:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.addcitizen.success");
                    Main.getMessageManager().sendMessage(citizen, Main.getMessageManager().getMessageFromConfig("guild.addcitizen.request").replace("%player%", player.getName()));
                    Main.getMessageManager().sendMessageFromConfig(citizen, "guild.addcitizen.requestinstruction");
                    return true;
                case 2:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.addcitizen.inotherguildcitizen");
                    return true;
                case 3:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.addcitizen.alreadycitizen");
                    return true;
                case 4:
                    Main.getMessageManager().sendMessageFromConfig(player, "guild.addcitizen.noguild");
                    return true;
            }
        }

        return false;
    }

}
