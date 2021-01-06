package me.TurtlesAreHot.BrickThrower.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrepareCraftEvent implements Listener {
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		for (HumanEntity entity : e.getViewers()) {
			if (entity instanceof Player) {
				CraftingInventory g = e.getInventory();
				for (ItemStack item : g.getContents()) {
					ItemMeta meta = item.getItemMeta();
					if(item.getType() == Material.BRICK && meta.getDisplayName().equals("Heavy Brick")) {
						List<String> lores = meta.getLore();
						for (String lore : lores) {
							String fix_lore = lore.replaceAll("§", "");
							String server_ver = Bukkit.getVersion();
							String version = server_ver.substring(server_ver.indexOf("(MC: ")+5, server_ver.indexOf("(MC: ")+9); // gets the version without the end number.
							//For some unknown reason to me, on 1.16 the lore gets set to "on_ft" instead... I really cannot figure it out. Any ideas please reply!
							if(version.equals("1.16")) {
								if(fix_lore.equals("on_ft")) {
									e.getInventory().setResult(new ItemStack(Material.AIR));
									break;
								}
							}
							else if (fix_lore.equals("no_craft")) {
								e.getInventory().setResult(new ItemStack(Material.AIR));
								break;
							}
						}
					}
				}
			}
		}
	}
	
}
