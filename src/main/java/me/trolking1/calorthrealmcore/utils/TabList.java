package me.trolking1.calorthrealmcore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.trolking1.calorthrealmcore.Main;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TabList {

    public void createTabList(Player player) {
        try {

            //remove player
            /*
            Object[] removedPlayers = new Object[Bukkit.getOnlinePlayers().size()];

            int counter = 0;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Object craftPlayer = Main.getNmsUtil().getOBCClass("entity.CraftPlayer").cast(onlinePlayer);
                Object handle = Main.getNmsUtil().getOBCClass("entity.CraftPlayer").getMethod("getHandle").invoke(craftPlayer);
                removedPlayers[counter] = handle;
                counter++;
            }

            Class<?>[] declaredClasses = Main.getNmsUtil().getNMSClass("PacketPlayOutPlayerInfo").getDeclaredClasses();
            Object enumPlayerInfoAction = declaredClasses[1].getField("REMOVE_PLAYER").get(null);
            Class<?> entityPlayers = Class.forName("[L" + Main.getNmsUtil().getNMSClass("EntityPlayer").getName() + ";");
            Constructor<?> playerInfoConstructor = Main.getNmsUtil().getNMSClass("PacketPlayOutPlayerInfo").getConstructor(declaredClasses[1], entityPlayers);
            Object packet = playerInfoConstructor.newInstance(enumPlayerInfoAction, removedPlayers);

            Main.getNmsUtil().sendPacket(player, packet);
            */

            Collection<? extends Player> playersBukkit = Bukkit.getOnlinePlayers();
            EntityPlayer[] playersNMS = new EntityPlayer[playersBukkit.size()];
            int current = 0;
            for (Player p : playersBukkit) {
                playersNMS[current] = ((CraftPlayer) p).getHandle();
                current++;
            }
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playersNMS);

            Main.getNmsUtil().sendPacket(player, packet);

            //add players
            List<EntityPlayer> addedPlayers = new ArrayList<>();
            for (int i = 0; i < 60; i++) {
                if (i == 0 || i == 20 || i == 40) {
                    if (i < 10) {
                        addedPlayers.add((EntityPlayer) createPlayer("0" + String.valueOf(i), "&5Then 10 then", "eyJ0aW1lc3RhbXAiOjE1MDk0NzAwODE5MDAsInByb2ZpbGVJZCI6ImM0YzRjNTQ0YWIwMDQyMTk4YmNmZTg5MmFlZTY1YjkyIiwicHJvZmlsZU5hbWUiOiJ0cm9sa2luZzEiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I5ODE0YzQ3OWZjMzg4MmZiZWZhNmUwYWE5NDEyZWE0M2ZhNWMzYWJkOTc1NGFjNmY0NTM2ZGFjYzE4MDdlIn19fQ==", "s8oPnXBEFGJRg4wafZWU4QP7dsvEuTAWTGlx4PlwRScOyT5WuaBVj9RtVKF9PVBW122rj7HSmn+ShwoYWWjuvoOjUcs8HdgTAPgU9xGGADZ84WNSeJwoykXUXvS2NswK9cJWL3L5GFs0uAUM7EOrqciipCPNpxD14XPeblBGnNWr8DSvx6YISIabyi5CxLMdx6VridVE2muKKWV7IMxn09oFaE6vJnlzFE1Oxv65HMV3hdz7Nlt3M8BV45IoKiMc+PqHWOaEIyDzQLmFLT108VO4QS1p4p1Fqu4sb0qnHvESHV8bomd4JYIZBJCFQ90x8PTJh54+Ub/Y4OcVMvIeMSb4bxfHTxaTLb3My+0+hBO0gK99tYOGOa5orBZ6J7JvC1pMpHCVv2Z75PSd60rdMfwloIglc7KROpgZyXY0MBvZTUKZS7MeGygKpT/9PUCkd9oBhLIDuwQUa5S4bLWAO5g7fXwSPL5fjAZbG2AVVgN3TfwtIJ3P5jrGgkTR7hlopgSmcSQ0vjuU4TjQUQU/oO5V7VaaUOnPTSNp3zUwvYEqcZ8Vmw3umluMiUGAvPgZmkqbzpXrfhmVPI8whG6TFSI3GCx3M7ay8l+KXQr8tX2bLNDFmhu+m/WIEwNR7qXSaUc5cQHF8YziQcakj3/KmDtS/p3sHv7+7b9prCywx60="));
                    } else {
                        addedPlayers.add((EntityPlayer) createPlayer(String.valueOf(i), "&5Then 10 then", "eyJ0aW1lc3RhbXAiOjE1MDk0NzAwODE5MDAsInByb2ZpbGVJZCI6ImM0YzRjNTQ0YWIwMDQyMTk4YmNmZTg5MmFlZTY1YjkyIiwicHJvZmlsZU5hbWUiOiJ0cm9sa2luZzEiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I5ODE0YzQ3OWZjMzg4MmZiZWZhNmUwYWE5NDEyZWE0M2ZhNWMzYWJkOTc1NGFjNmY0NTM2ZGFjYzE4MDdlIn19fQ==", "s8oPnXBEFGJRg4wafZWU4QP7dsvEuTAWTGlx4PlwRScOyT5WuaBVj9RtVKF9PVBW122rj7HSmn+ShwoYWWjuvoOjUcs8HdgTAPgU9xGGADZ84WNSeJwoykXUXvS2NswK9cJWL3L5GFs0uAUM7EOrqciipCPNpxD14XPeblBGnNWr8DSvx6YISIabyi5CxLMdx6VridVE2muKKWV7IMxn09oFaE6vJnlzFE1Oxv65HMV3hdz7Nlt3M8BV45IoKiMc+PqHWOaEIyDzQLmFLT108VO4QS1p4p1Fqu4sb0qnHvESHV8bomd4JYIZBJCFQ90x8PTJh54+Ub/Y4OcVMvIeMSb4bxfHTxaTLb3My+0+hBO0gK99tYOGOa5orBZ6J7JvC1pMpHCVv2Z75PSd60rdMfwloIglc7KROpgZyXY0MBvZTUKZS7MeGygKpT/9PUCkd9oBhLIDuwQUa5S4bLWAO5g7fXwSPL5fjAZbG2AVVgN3TfwtIJ3P5jrGgkTR7hlopgSmcSQ0vjuU4TjQUQU/oO5V7VaaUOnPTSNp3zUwvYEqcZ8Vmw3umluMiUGAvPgZmkqbzpXrfhmVPI8whG6TFSI3GCx3M7ay8l+KXQr8tX2bLNDFmhu+m/WIEwNR7qXSaUc5cQHF8YziQcakj3/KmDtS/p3sHv7+7b9prCywx60="));
                    }
                } else {
                    if (i < 10) {
                        addedPlayers.add((EntityPlayer) createPlayer("0" + String.valueOf(i), String.valueOf(i), null, null));
                    } else {
                        addedPlayers.add((EntityPlayer) createPlayer(String.valueOf(i), String.valueOf(i), null, null));
                    }
                }
            }

            Packet p2 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, addedPlayers);
            Main.getNmsUtil().sendPacket(player, p2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object createPlayer(String name, String listName, String texture, String signature) {
        try {
            Object craftServer = Main.getNmsUtil().getOBCClass("CraftServer").cast(Bukkit.getServer());
            Object minecraftServer = Main.getNmsUtil().getNMSClass("MinecraftServer").cast(Main.getNmsUtil().getOBCClass("CraftServer").getMethod("getServer").invoke(craftServer));
            Object worldServer = Main.getNmsUtil().getNMSClass("MinecraftServer").getMethod("getWorldServer").invoke(0);

            Constructor<?> playerInteractManagerConstructor = Main.getNmsUtil().getNMSClass("PlayerInteractManager").getConstructor(Main.getNmsUtil().getNMSClass("WorldServer").getClass());
            Object playerInteractManager = playerInteractManagerConstructor.newInstance(worldServer);

            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
            if (texture != null && signature != null) {
                gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
            }

            Constructor<?> entityPlayerConstructor  = Main.getNmsUtil().getNMSClass("EntityPlayer ").getConstructor(Main.getNmsUtil().getNMSClass("MinecraftServer"), Main.getNmsUtil().getNMSClass("WorldServer"), GameProfile.class, Main.getNmsUtil().getNMSClass("PlayerInteractManager"));
            Object entityPlayer = entityPlayerConstructor.newInstance(minecraftServer, worldServer, gameProfile, playerInteractManager);

            Constructor<?> chatComponentTextConstructor = Main.getNmsUtil().getNMSClass("ChatComponentText").getConstructor(String.class);
            Object chatComponentText = chatComponentTextConstructor.newInstance(ChatColor.translateAlternateColorCodes('&', listName));

            entityPlayer.getClass().getField("listName").set(null, chatComponentText);


            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer ws = server.getWorldServer(0);
            PlayerInteractManager playerinteractmanager = new PlayerInteractManager(ws);
            GameProfile profile = new GameProfile(UUID.randomUUID(), name);
            profile.getProperties().put("textures", new Property("textures", texture, signature));
            EntityPlayer player = new EntityPlayer(server, ws, profile, playerinteractmanager);
            player.listName = new ChatComponentText(listName);

            return entityPlayer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
