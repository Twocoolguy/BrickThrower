package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;

public class SmithingEvent implements Listener {
    @EventHandler
    public void onSmithing(PrepareSmithingEvent e) {
        ItemStack[] contents = e.getInventory().getContents();

        for(int i = 0; i < contents.length; i++) {
            if(contents[i] != null) {
                if(Config.getNBTData(contents[i], "brickthrower_item") != null) {
                    e.setResult(new ItemStack(Material.AIR));
                }
            }
        }
    }

}
