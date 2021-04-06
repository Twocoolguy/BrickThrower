package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantEvent implements Listener {
    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e) {
        e.getItem();
        if (Config.getNBTData(e.getItem(), "brickthrower_item") != null) {
            e.setCancelled(true);
        }
        else {
            ItemStack[] inv = e.getInventory().getContents();
            if(!Config.twelveAndBelow()) {
                for (int i = 0; i < inv.length; i++) {
                    if (inv[i] != null) {
                        if (inv[i].getType() == Material.LAPIS_LAZULI) {
                            if (Config.getNBTData(inv[i], "brickthrower_item") != null) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
