package me.TurtlesAreHot.BrickThrower.events;

import me.TurtlesAreHot.BrickThrower.version.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Config;
import me.TurtlesAreHot.BrickThrower.Main;

public class PlayerClickEvent implements Listener {
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		Action action = event.getAction();
		if(!(action == Action.RIGHT_CLICK_AIR) && !(action == Action.RIGHT_CLICK_BLOCK)) {
			// Checks and sees what the event action is. If it isn't a right click on a block or air we do not want to do anything.
			return;
		}
		if (event.getItem() == null) {
			// Checks if the event item is air, if it is we do not want to do anything because the program will error.
			return;
		}
		Player p = event.getPlayer();
		String version = Config.getServerVersion(); // Gives us the server version.
		if(version.equals("1.8")) {
			if(p.getInventory().getItem(p.getInventory().getHeldItemSlot()) == null) {
				return;
			}
		}
		else if (p.getInventory().getItemInMainHand() == null) {
			return;
		}
		// Getting item in mainhand, this gets exactly what it is with the names.
		ItemStack held = null;
		if(version.equals("1.8")) {
			held = p.getInventory().getItem(p.getInventory().getHeldItemSlot());
		}
		else {
			held = p.getInventory().getItemInMainHand();
		}
		String info = null;
		switch(version) {
			case "1.13":
				info = NBT13.getNBTDataString(held, "brickthrower_item");
				break;
			case "1.12":
				info = NBT12.getNBTDataString(held, "brickthrower_item");
				break;
			case "1.11":
				info = NBT11.getNBTDataString(held, "brickthrower_item");
				break;
			case "1.10":
				info = NBT10.getNBTDataString(held, "brickthrower_item");
				break;
			case "1.9":
				info = NBT9.getNBTDataString(held, "brickthrower_item");
				break;
			case "1.8":
				info = NBT8.getNBTDataString(held, "brickthrower_item");
				break;
			default:
				info = NBT14.getNBTDataString(held, "brickthrower_item");

		}
		if(info == null) {
			return;
		}
		if(info.equals("true")) {
			if(event.isBlockInHand()) {
				//Checks if there is something that you can place in your hand.
				if(action == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() != Material.AIR) {
					// Canceling the event that places a block.
					event.setCancelled(true);
				}
			}
			if(held.getType().isEdible()) {
				event.setCancelled(true);
			}
			// Creates a itemstack of bricks with just one ands sets up all information about the brick.
			ItemStack brick = new ItemStack(held.getType(), 1);
			ItemMeta brick_im = brick.getItemMeta();
			if (brick_im == null) {
				return;
			}
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
			ItemStack eventItem = event.getItem();
			if(eventItem.equals(held)) {
				// This checks if the item that is trying to be thrown is in the off hand. Otherwise it doesn't throw it and it also does not remove any.
				if(!(Config.twelveAndAbove()) && held.getAmount() == 1) {
					event.getPlayer().getInventory().remove(held);
				}
				else {
					event.getItem().setAmount(held.getAmount()-1);
				}
			}
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
