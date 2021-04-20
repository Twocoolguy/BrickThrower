package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.inventory.ItemStack;

public class BrewingFuelEvent implements Listener {
    @EventHandler
    public void onBrewFuel(BrewingStandFuelEvent e) {
        ItemStack fuel = e.getFuel();
        if (Config.getNBTData(fuel, "brickthrower_item") != null) {
            e.setCancelled(true);
        }
    }
}
