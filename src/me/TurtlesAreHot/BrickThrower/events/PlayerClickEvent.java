package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.version.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Config;
import me.TurtlesAreHot.BrickThrower.Main;

public class PlayerClickEvent implements Listener {
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		if (event.getItem() == null) {
			// Checks if the event item is air, if it is we do not want to do anything because the program will error.
			return;
		}
		Player p = event.getPlayer();
		// Getting item in mainhand, this gets exactly what it is with the names.
		ItemStack held = p.getInventory().getItemInMainHand();
		String info = null;
		String version = Config.getServerVersion(); // Gives us the server version.
		switch(version) {
			case "1.13":
				info = NBT13.getNBTDataString(held, "brickthrower_item");
			case "1.12":
				info = NBT12.getNBTDataString(held, "brickthrower_item");
			case "1.11":
				info = NBT11.getNBTDataString(held, "brickthrower_item");
			case "1.10":
				info = NBT10.getNBTDataString(held, "brickthrower_item");
			case "1.9":
				info = NBT9.getNBTDataString(held, "brickthrower_item");
			case "1.8":
				info = NBT8.getNBTDataString(held, "brickthrower_item");
			default:
				info = NBT14.getNBTDataString(held, "brickthrower_item");
		}
		if(info != null) {
			// Creates a itemstack of bricks with just one ands sets up all information about the brick.
			ItemStack brick = new ItemStack(held.getType(), 1);
			ItemMeta brick_im = brick.getItemMeta();
			brick_im.setDisplayName(Config.getItemName());
			brick.setItemMeta(brick_im);
			// Gets the location of the brick and then sets its velocity and pickupdelay to make the item launch, and make it so you cannot pick it up.
			Item brock = p.getWorld().dropItem(p.getEyeLocation(), brick);
			brock.setVelocity(p.getEyeLocation().getDirection());
			int itemTime = Config.getDisappearTime();
			if(itemTime != 0) {
				brock.setPickupDelay(Short.MAX_VALUE);
			}
			// Removes one of the bricks from the players inventory.
			event.getItem().setAmount(event.getItem().getAmount()-1);
			// After the time in seconds defined in the config remove the brick.
			if(itemTime != 0) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class),  new Runnable() {
					@Override
					public void run() {
						brock.remove();
					}
				}, itemTime*20L);
			}
		}
		
	}
}
