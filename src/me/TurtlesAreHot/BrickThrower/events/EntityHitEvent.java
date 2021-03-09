package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class EntityHitEvent implements Listener {
    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent e) {
        if(e.getDamager().getType() == EntityType.PLAYER) {
            Player p = (Player) e.getDamager();
            PlayerInventory pi = p.getInventory();
            if(pi.getItem(p.getInventory().getHeldItemSlot()) != null) {
                if(Config.getNBTData(pi.getItem(p.getInventory().getHeldItemSlot()), "brickthrower_item") != null) {
                    e.setCancelled(true);
                }
            }

        }
    }
}
