package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == null) {
            return;
        }
        InventoryType cit = e.getClickedInventory().getType();
        if(cit == InventoryType.STONECUTTER ||
            cit == InventoryType.CARTOGRAPHY ||
            cit == InventoryType.LOOM) {
            if(Config.getNBTData(e.getCursor(), "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }
    }
}
