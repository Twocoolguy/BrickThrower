package me.TurtlesAreHot.BrickThrower.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import me.TurtlesAreHot.BrickThrower.GeneralMethods;

public class PrepareCraftEvent implements Listener {
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		String server_ver = Bukkit.getVersion();
		String version = server_ver.substring(server_ver.indexOf("(MC: ")+5, server_ver.indexOf("(MC: ")+9); // gets the version without the end number.
		//For some unknown reason to me, on 1.16 the lore gets set to "on_ft" instead... I really cannot figure it out. Any ideas please reply!
		for (HumanEntity entity : e.getViewers()) {
			if (entity instanceof Player) {
				CraftingInventory g = e.getInventory();
				for (ItemStack item : g.getContents()) {
					String info = null;
					if (version.equalsIgnoreCase("1.13")) {
						info = GeneralMethods.getNBTDataString13(item, "brickthrower_item");
					}
					else {
						info = GeneralMethods.getNBTDataString(item, "brickthrower_item");
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
