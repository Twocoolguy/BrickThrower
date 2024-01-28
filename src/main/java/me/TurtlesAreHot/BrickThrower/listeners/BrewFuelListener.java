package me.TurtlesAreHot.BrickThrower.listeners;

import me.TurtlesAreHot.BrickThrower.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewingStandFuelEvent;

public class BrewFuelListener implements Listener {

    @EventHandler
    public void onBrewFuel(BrewingStandFuelEvent e) {
        if(Main.getNBTData(e.getFuel(), "brickthrower_item") != null) {
            e.setCancelled(true);
        }
    }
}
