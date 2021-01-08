package me.TurtlesAreHot.BrickThrower.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.GeneralMethods;
import me.TurtlesAreHot.BrickThrower.Main;

public class PlayerClickEvent implements Listener {
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (event.getItem() == null) {
			// Checks if the event item is air, if it is we do not want to do anything because the program will error.
			return;
		}
		// Getting item in mainhand, this gets exactly what it is with the names.
		ItemStack held = p.getInventory().getItemInMainHand();
		String info = null;
		String server_ver = Bukkit.getVersion();
		String version = server_ver.substring(server_ver.indexOf("(MC: ")+5, server_ver.indexOf("(MC: ")+9);
		if (version.equalsIgnoreCase("1.13")) {
			info = GeneralMethods.getNBTDataString13(held, "brickthrower_item");
		}
		else {
			info = GeneralMethods.getNBTDataString(held, "brickthrower_item");
		}
		if(info != null) {
			// Creates a itemstack of bricks with just one ands sets up all information about the brick.
			ItemStack brick = new ItemStack(held.getType(), 1);
			ItemMeta brick_im = brick.getItemMeta();
			brick_im.setDisplayName("Heavy Brick");
			brick.setItemMeta(brick_im);
			// Gets the location of the brick and then sets its velocity and pickupdelay to make the item launch, and make it so you cannot pick it up.
			Location brick_loc = p.getEyeLocation();
			Item brock = p.getWorld().dropItem(brick_loc, brick);
			brock.setVelocity(brick_loc.getDirection());
			brock.setPickupDelay(Short.MAX_VALUE);
			// Removes one of the bricks from the players inventory.
			event.getItem().setAmount(event.getItem().getAmount()-1);
			// After 2 seconds remove the brick.
			Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class),  new Runnable() {
				@Override
				public void run() {
					brock.remove();
				}
			}, 2*20L);
		}
		
	}
}
