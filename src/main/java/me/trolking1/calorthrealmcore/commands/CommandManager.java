package me.trolking1.calorthrealmcore.commands;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.commands.admin.AdminMainCommand;
import me.trolking1.calorthrealmcore.commands.admin.general.SetSpawn;
import me.trolking1.calorthrealmcore.commands.admin.general.SetTutorialSpawn;
import me.trolking1.calorthrealmcore.commands.clan.ClanMainCommand;
import me.trolking1.calorthrealmcore.commands.guild.GuildMainCommand;
import org.bukkit.plugin.Plugin;

/**
 * Created by Gabriel on 4/3/2017.
 */
public class CommandManager {

    private NewCommand guild, clan, admin;

    public CommandManager() {
        guild = new NewCommand("guild");
        clan = new NewCommand("clan");
        admin = new NewCommand("admin");

        setupGuildCommands();
    }

    private void setupGuildCommands() {
        guild.addCommand("guild", new GuildMainCommand());
        Main.main.getCommand("guild").setExecutor(guild);

        clan.addCommand("clan", new ClanMainCommand());
        Main.main.getCommand("clan").setExecutor(clan);


        admin.addCommand("admin", new AdminMainCommand());
        admin.addCommand("setspawn", new SetSpawn());
        admin.addCommand("settutorialspawn", new SetTutorialSpawn());
        Main.main.getCommand("admin").setExecutor(admin);
    }

}
