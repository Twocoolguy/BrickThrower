package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class InteractEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        PlayerInventory pi = e.getPlayer().getInventory();
        if(!Config.getServerVersion().equals("1.8")) {
            if(pi.getItemInOffHand() != null) {
                if (Config.getNBTData(pi.getItemInOffHand(), "brickthrower_item") != null) {
                    e.setCancelled(true);
                }
            }
        }
        if(pi.getItemInMainHand() != null) {
            if(Config.getNBTData(pi.getItemInMainHand(), "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }
    }
}
