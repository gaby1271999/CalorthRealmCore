package me.trolking1.calorthrealmcore.events;

import me.trolking1.calorthrealmcore.Main;
import me.trolking1.calorthrealmcore.menu.Item;
import me.trolking1.calorthrealmcore.menu.accounselector.AccountSelector;
import me.trolking1.calorthrealmcore.playerinfo.classes.PlayerClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by Gabriel on 5/2/2017.
 */
public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws InstantiationException, IllegalAccessException {
        Player player = event.getPlayer();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getItemMeta() != null) {
                if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().equals(((Item) Main.getConfigManager().getAccountSelector().getConfig().get("selector.item")).getItem().getItemMeta().getDisplayName())) {
                        AccountSelector accountSelector = new AccountSelector(player);
                        accountSelector.openMenu(player);
                    }
                }
            }
        }

        if (Main.getPlayerInfoManager().loggedIn(player)) {
            PlayerClass playerClass = Main.getPlayerInfoManager().getPlayerData(player).getPlayerClass();
            if (player.getItemInHand().getItemMeta() != null) {
                if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().equals(playerClass.getMainItem().getItem().getItemMeta().getDisplayName())) {
                        String spell = "";

                        if (player.hasMetadata("spell")) {
                            spell = (String) player.getMetadata("spell").get(0).value();

                            spell += ("-" + transformClick(event.getAction()));

                            String[] parts = spell.split("-");
                            if (parts.length == 3) {
                                playerClass.activateAbility(player, spell);
                                player.removeMetadata("spell", Main.getMain());
                                return;
                            } else {
                                player.setMetadata("spell", new FixedMetadataValue(Main.getMain(), spell));
                                return;
                            }
                        } else {
                            player.setMetadata("spell", new FixedMetadataValue(Main.getMain(), transformClick(event.getAction())));
                            return;
                        }
                    }
                }
            }
        }
    }

    private String transformClick(Action action) {
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            return "LEFT";
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            return "RIGHT";
        } else {
            return "";
        }
    }
}
