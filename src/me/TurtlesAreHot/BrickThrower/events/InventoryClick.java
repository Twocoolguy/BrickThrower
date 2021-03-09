package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getInventory() == null) {
            return;
        }
        if(e.getInventory().getType() == null) {
            return;
        }
        InventoryType cit = e.getInventory().getType();
        if(cit == InventoryType.STONECUTTER ||
            cit == InventoryType.CARTOGRAPHY ||
            cit == InventoryType.LOOM) {
            if(Config.getNBTData(e.getCurrentItem(), "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }
        //slots for armor:
        //helmet: 39
        //chestplate: 38
        //leggings: 37
        //boots: 36
        //Client side it may look like the armor still goes on sometimes, but really it isn't there.
        if(e.getAction() == InventoryAction.PLACE_ALL ||
                e.getAction() == InventoryAction.PLACE_ONE ||
                e.getAction() == InventoryAction.PLACE_SOME ||
        e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
            if (e.getClickedInventory().getType() != null) {
                InventoryType it = e.getClickedInventory().getType();
                if (it == InventoryType.PLAYER) {
                    if (e.getCursor() != null) {
                        if(Config.getNBTData(e.getCursor(), "brickthrower_item") != null) {
                            int slotClicked = e.getSlot();
                            if(slotClicked == 39 ||
                            slotClicked == 38 ||
                            slotClicked == 37 ||
                            slotClicked == 36) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
