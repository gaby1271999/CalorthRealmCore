package me.trolking1.calorthrealmcore.commands.admin.mobspawnregions;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.InvalidClassException;

public class MobRegionCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Main.getMessageManager().sendArrayMessageFromConfig(player, "admin.mobregion.info");
        } else if (args.length == 3) {
            if (args[1].toLowerCase().equals("delete")) {
                int id = getId(player, args[2]);
                if (id != 0) {
                    int result = Main.getCustomMobManager().deleteRegion(id);

                    if (result == 1) {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.delete.succes");
                        return false;
                    } else {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.idnotexisting");
                        return false;
                    }
                }

                return false;
            } else if (args[1].toLowerCase().equals("addspawn")) {
                int id = getId(player, args[2]);
                if (id != 0) {
                    int result = Main.getCustomMobManager().addSpawn(id, player.getLocation());

                    if (result == 1) {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.addspawn.succes");
                        return false;
                    } else {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.idnotexisting");
                        return false;
                    }
                }

                return false;
            } else if (args[1].toLowerCase().equals("removespawn")) {
                int id = getId(player, args[2]);
                if (id != 0) {
                    int result = Main.getCustomMobManager().removeSpawn(id, player.getLocation());

                    if (result == 1) {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.removespawn.succes");
                        return false;
                    } else if (result == 3) {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.removespawn.spawndoesnotexists");
                        return false;
                    } else {
                        Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.idnotexisting");
                        return false;
                    }
                }

                return false;
            } else {
                int mobId = getId(player, args[1]);
                byte amount = Main.intToByte(getNumber(player, args[2]));

                int result = Main.getCustomMobManager().createMobSpawn(mobId, amount);

                if (result == 0) {
                    Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.create.wrongmobid");
                    return false;
                } else {
                    Main.getMessageManager().sendMessage(player, Main.getMessageManager().getMessageFromConfig("admin.mobregion.create.succes").replace("%id%", String.valueOf(result)));
                    return false;
                }
            }
        }

        return false;
    }

    private int getId(Player player, String num) {
        try {
            return Integer.valueOf(num);
        } catch (NumberFormatException exception) {
            Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.wrongid");
            return 0;
        }
    }

    private int getNumber(Player player, String num) {
        try {
            return Integer.valueOf(num);
        } catch (NumberFormatException exception) {
            Main.getMessageManager().sendMessageFromConfig(player, "admin.mobregion.wrongnumber");
            return 0;
        }
    }
}
