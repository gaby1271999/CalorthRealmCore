package me.trolking1.calorthrealmcore.guilds;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Created by Gabriel on 3/30/2017.
 */
public class GuildBank {

    /*

    guild id
    item id
    amount
    item model (1-*)

     */

    private NPC npc;

    public GuildBank(Location guildBankLocation) {
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Banker");

        setupNPC(guildBankLocation);
    }

    private void setupNPC(Location location) {
        npc.setName(ChatColor.translateAlternateColorCodes('&', Main.configManager.getGuildBank().getConfig().getString("npc.displayname")));
        npc.data().set(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_METADATA, Main.configManager.getGuildBank().getConfig().getString("npc.skin.value"));
        npc.data().set(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_SIGN_METADATA, Main.configManager.getGuildBank().getConfig().getString("npc.skin.signature"));

        npc.spawn(location);
    }
}
