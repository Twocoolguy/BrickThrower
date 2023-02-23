package me.TurtlesAreHot.BrickThrower.events;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import me.TurtlesAreHot.BrickThrower.Config;

public class PrepareCraftEvent implements Listener {
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		for (HumanEntity entity : e.getViewers()) {
			if (entity instanceof Player) {
				CraftingInventory g = e.getInventory();
				for (ItemStack item : g.getContents()) {
					String info = Config.getNBTData(item, "brickthrower_item");
					if(info != null) {
						e.getInventory().setResult(new ItemStack(Material.AIR));
						break;
					}
				}
			}
		}
	}
	
}
