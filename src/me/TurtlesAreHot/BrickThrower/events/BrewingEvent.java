package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

public class BrewingEvent implements Listener {
    @EventHandler
    public void onBrew(BrewEvent e) {
        ItemStack[] contents = e.getContents().getContents();
        for(int i = 0; i < contents.length; i++) {
            if(contents[i] != null) {
                if(Config.getNBTData(contents[i], "brickthrower_item") != null) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
