package me.trolking1.calorthrealmcore.commands;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 3/29/2017.
 */
public abstract class CommandInterface {

    private String permission, descriptionPath;
    private String[] names, usage;
    private Map<String, Integer> args = new HashMap<>();

    public CommandInterface(String permission, String descriptionPath, String... names) {
        this.names = names;
        this.permission = permission;
        this.descriptionPath = descriptionPath;
    }

    public abstract boolean excecute(Player player, String[] args);

    public String[] getNames() {
        return names;
    }

    public boolean checkPerms(Player player) {
        if (this.permission != null) {
            return player.hasPermission(this.permission);
        }

        return true;
    }

    public void setArgs(Map<String, Integer> args) {
        this.args = args;
    }

    public Map<String, Integer> getArgs() {
        return args;
    }

    private List<String> getArg(int index) {
        List<String> args = new ArrayList<>();

        for (String arg : this.args.keySet()) {
            if (this.args.get(arg) == index) {
                args.add(arg);
            }
        }

        return args;
    }

    private int getArgsLength() {
        int argsCount = 0;

        for (String arg : args.keySet()) {
            if (args.get(arg) > argsCount) {
                argsCount = args.get(arg);
            }
        }

        return argsCount;
    }

    public boolean checkArgs(String... args) {
        if (getArgsLength() <= args.length) {
            for (int i = 0; i < args.length; i++) {
                if (getArgsLength() >= i) {
                    for (String arg : getArg(i)) {
                        if (args[i].toLowerCase().equals(arg.toLowerCase())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public void setUsage(String... usage) {
        this.usage = usage;
    }

    public void sendCommandInfo(Player player) {
        Main.getMessageManager().sendEmptyMessage(player, this.sendUsage() + " &b: " + Main.getMessageManager().getMessageFromConfig(this.descriptionPath));
    }

    private String sendUsage() {
        String usage = "";
        for (String use : this.usage) {
            usage += use + " ";
        }

        return "&4&l/" + usage;
    }

    public void sendUsage(Player player) {
        Main.getMessageManager().sendMessage(player, "&4&l/" + sendUsage());
    }
}
