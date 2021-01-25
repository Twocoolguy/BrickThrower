package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.version.*;
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
					String info = null;
					String version = Config.getServerVersion();
					switch(version) {
						case "1.13":
							info = NBT13.getNBTDataString(item, "brickthrower_item");
						case "1.12":
							info = NBT12.getNBTDataString(item, "brickthrower_item");
						case "1.11":
							info = NBT11.getNBTDataString(item, "brickthrower_item");
						case "1.10":
							info = NBT10.getNBTDataString(item, "brickthrower_item");
						case "1.9":
							info = NBT9.getNBTDataString(item, "brickthrower_item");
						case "1.8":
							info = NBT8.getNBTDataString(item, "brickthrower_item");
						default:
							info = NBT14.getNBTDataString(item, "brickthrower_item");
					}
					if(info != null) {
						e.getInventory().setResult(new ItemStack(Material.AIR));
						break;
					}
				}
			}
		}
	}
	
}
