package me.trolking1.calorthrealmcore;

import com.zaxxer.hikari.HikariDataSource;
import me.lucko.luckperms.api.LuckPermsApi;
import me.trolking1.calorthrealmcore.clan.ClanManager;
import me.trolking1.calorthrealmcore.commands.CommandManager;
import me.trolking1.calorthrealmcore.customitems.Bow;
import me.trolking1.calorthrealmcore.customitems.Sword;
import me.trolking1.calorthrealmcore.customitems.Wand;
import me.trolking1.calorthrealmcore.custommobs.CustomMob;
import me.trolking1.calorthrealmcore.custommobs.CustomMobManager;
import me.trolking1.calorthrealmcore.custommobs.MobSpawn;
import me.trolking1.calorthrealmcore.database.Database;
import me.trolking1.calorthrealmcore.events.*;
import me.trolking1.calorthrealmcore.events.entitymove.EntityMoveManager;
import me.trolking1.calorthrealmcore.guilds.GuildManager;
import me.trolking1.calorthrealmcore.guilds.utils.GuildUtils;
import me.trolking1.calorthrealmcore.menu.MenuManager;
import me.trolking1.calorthrealmcore.playerinfo.PlayerInfoManager;
import me.trolking1.calorthrealmcore.playerinfo.ability.FireEffect;
import me.trolking1.calorthrealmcore.playerinfo.ability.archer.RainOfFire;
import me.trolking1.calorthrealmcore.playerinfo.ability.archer.Windstorm;
import me.trolking1.calorthrealmcore.playerinfo.classes.Archer;
import me.trolking1.calorthrealmcore.utils.NMSUtil;
import me.trolking1.calorthrealmcore.utils.ScoreBoardUtil;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.List;

public class Main extends JavaPlugin {

	static {
		ConfigurationSerialization.registerClass(Bow.class);
		ConfigurationSerialization.registerClass(Sword.class);
		ConfigurationSerialization.registerClass(Wand.class);
		ConfigurationSerialization.registerClass(me.trolking1.calorthrealmcore.menu.Item.class);
		ConfigurationSerialization.registerClass(Windstorm.class);
		ConfigurationSerialization.registerClass(RainOfFire.class);
		ConfigurationSerialization.registerClass(FireEffect.class);
		ConfigurationSerialization.registerClass(Archer.class);
		ConfigurationSerialization.registerClass(CustomMob.class);
		ConfigurationSerialization.registerClass(MobSpawn.class);
	}

	private static Permission perms = null;
	private static Economy econ = null;
	private static ConfigManager configManager;
	private static HikariDataSource hikari;
	private static MenuManager menuManager;
	private static PlayerInfoManager playerInfoManager;
	private static ClanManager clanManager;
	private static MessageManager messageManager;
	private static Database database;
	private static GuildManager guildManager = new GuildManager();
	private static CustomMobManager customMobManager;
	private static GuildUtils guildUtils;
	private static NMSUtil nmsUtil;
	private static ScoreBoardUtil scoreBoardUtil;
	private CommandManager commandManager;
	private static Main main;

	public void onEnable() {
		main = this;
		configManager = new ConfigManager(this);

		database = new Database();
		if (!database.createDBConnection()) {
			Bukkit.getPluginManager().disablePlugin(this);
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "The plugin has been shut down due to failed db connection or not found.");
			return;
		}

		messageManager = new MessageManager();
		menuManager = new MenuManager();
		guildManager.onStartUp();
		playerInfoManager = new PlayerInfoManager();
		customMobManager = new CustomMobManager();
		customMobManager.setupMobSpawns();
		clanManager = new ClanManager();
		guildUtils = new GuildUtils();
		nmsUtil = new NMSUtil();
		scoreBoardUtil = new ScoreBoardUtil();
		commandManager = new CommandManager();

		registerEvents(new PlayerJoin(), new AccountSelectorEvent(), new PlayerInteract(), new PlayerQuit(), new EntityMove());

		setupPermissions();

		ServicesManager manager = Bukkit.getServicesManager();
		if (manager.isProvidedFor(LuckPermsApi.class)) {
			final LuckPermsApi api = manager.getRegistration(LuckPermsApi.class).getProvider();
		}

		new EntityMoveManager();

		for (Player player : Bukkit.getOnlinePlayers()) {
			Main.getPlayerInfoManager().setPlayerAccounts(player);

			if (Main.getConfigManager().getConfig().getConfig().getBoolean("tptospawn") && Main.getConfigManager().getConfig().getConfig().get("spawn") != null) {
				player.teleport((Location) Main.getConfigManager().getConfig().getConfig().get("spawn"));
			}

			getPlayerInfoManager().createAccountSelector(player);
		}
	}

	public void onDisable() {
		customMobManager.shutDown();

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getMetadata("id").isEmpty()) {
				Main.getDatabase().saveAccount(player);
				player.getInventory().clear();

				player.removeMetadata("id", Main.getMain());
				player.removeMetadata("playerdata", Main.getMain());
			}
		}
	}

	public static byte intToByte(int value) {
		Integer i = new Integer(value);
		return i.byteValue();
	}

	public static short intToShort(int value) {
		Integer i = new Integer(value);
		return i.shortValue();
	}

	private void registerEvents(Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener, this);
		}
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public static String getPlayerGroup(Player player, List<String> possibleGroups) {
		for (String group : possibleGroups) {
			if (player.hasPermission("group." + group)) {
				return group;
			}
		}
		return null;
	}

	public static String encodeInventory(ItemStack[] items) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeObject(items);

			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ItemStack[] decodeInventory(String string) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

			ItemStack[] items = (ItemStack[]) dataInput.readObject();

			dataInput.close();
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encodeObject(Object object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeObject(object);

			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object decodeObject(String string) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

			Object object = dataInput.readObject();

			dataInput.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Permission getPerms() {
		return perms;
	}

	public static Economy getEcon() {
		return econ;
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public static HikariDataSource getHikari() {
		return hikari;
	}

	public static MenuManager getMenuManager() {
		return menuManager;
	}

	public static PlayerInfoManager getPlayerInfoManager() {
		return playerInfoManager;
	}

	public static ClanManager getClanManager() {
		return clanManager;
	}

	public static MessageManager getMessageManager() {
		return messageManager;
	}

	public static Database getDatabase() {
		return database;
	}

	public static GuildManager getGuildManager() {
		return guildManager;
	}

	public static CustomMobManager getCustomMobManager() {
		return customMobManager;
	}

	public static GuildUtils getGuildUtils() {
		return guildUtils;
	}

	public static NMSUtil getNmsUtil() {
		return nmsUtil;
	}

	public static ScoreBoardUtil getScoreBoardUtil() {
		return scoreBoardUtil;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public static Main getMain() {
		return main;
	}
}
