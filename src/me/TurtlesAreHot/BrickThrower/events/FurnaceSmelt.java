package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

public class FurnaceSmelt implements Listener {
    @EventHandler
    public void onBurn(FurnaceBurnEvent e) {
        if (e.getBlock().getType() == Material.FURNACE) {
            Furnace fur = (Furnace) e.getBlock().getState();
            FurnaceInventory fi = fur.getInventory();
            ItemStack[] contents = fi.getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null) {
                    if(Config.getNBTData(contents[i], "brickthrower_item") != null) {
                        e.setCancelled(true);
                    }
                }
            }
            if (Config.getNBTData(fi.getFuel(), "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }
    }
}
