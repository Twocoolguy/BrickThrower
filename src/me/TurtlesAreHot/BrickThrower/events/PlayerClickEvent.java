package me.TurtlesAreHot.BrickThrower.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Config;
import me.TurtlesAreHot.BrickThrower.Main;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.List;

public class PlayerClickEvent implements Listener {


	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		Action action = event.getAction();
		if(!Config.getAllowInteracts()) {
			String version = Config.getServerVersion(); // Gives us the server version.
			PlayerInventory pi = event.getPlayer().getInventory();
			if(pi.getItem(pi.getHeldItemSlot()) != null) {
				if(Config.getNBTData(pi.getItem(pi.getHeldItemSlot()), "brickthrower_item") != null) {
					event.setCancelled(true);
				}
			}
			if(!version.equals("1.8")) {
				if(pi.getItemInOffHand() != null) {
					if(Config.getNBTData(pi.getItemInOffHand(), "brickthrower_item") != null) {
						event.setCancelled(true);
					}
				}
			}
		}
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
		PlayerInventory pi = p.getInventory();
		if(version.equals("1.8")) {
			if(pi.getItem(pi.getHeldItemSlot()) == null) {
				return;
			}
		}
		else if (pi.getItemInMainHand() == null && pi.getItemInOffHand() == null) {
			// This is for checks on older versions of minecraft.
			return;
		}
		// Getting item in mainhand, this gets exactly what it is with the names.
		ItemStack held = null;
		if(version.equals("1.8")) {
			held = pi.getItem(p.getInventory().getHeldItemSlot());
		}
		else {
			if(pi.getItemInMainHand() == null || pi.getItemInMainHand().getType() == Material.AIR) {
				held = pi.getItemInOffHand();
			}
			else {
				held = pi.getItemInMainHand();
			}
		}
		String info = Config.getNBTData(held, "brickthrower_item");
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
				// This checks if the item that is trying to be thrown is in the off hand. Otherwise it doesn't throw it and it also does not remove any.w
				if(!(Config.twelveAndAbove()) && held.getAmount() == 1) {
					if(!(Config.getServerVersion().equals("1.8")) && (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR)) {
						p.getInventory().setItemInOffHand(null);
					}
					else {
						event.getPlayer().getInventory().remove(held);
					}
				}
				else {
					event.getItem().setAmount(held.getAmount() - 1);
				}
			}
			Date dropTime = new Date();
			// After the time in seconds defined in the config remove the brick.
			if(itemTime != 0) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class),  new Runnable() {
					@Override
					public void run() {
						brock.remove();
					}
				}, itemTime*20L);
			}
			double itemDamage = Config.getItemDamage();
			if(itemDamage > 0.0) {
				new BukkitRunnable() {
					@Override
					public void run() {
						if(itemTime != 0) {
							// This checks to make sure that this event is canceled when the item is removed after the time.
							Date currentTime = new Date();
							if((currentTime.getTime() - dropTime.getTime()) / 1000 >= itemTime) {
								cancel();
							}
						}
						// Get any entity that is nearby in this radius.
						List<Entity> near = brock.getNearbyEntities(0.5, 1.0, 0.5);
						for (Entity entity : near) {
							if (entity instanceof LivingEntity) {
								LivingEntity lEntity = (LivingEntity) entity;
								if (lEntity instanceof Player) {
									Player playerHit = (Player) lEntity;
									if (playerHit.getUniqueId().equals(p.getUniqueId())) {
										// Checks if the player who threw the item is the one getting hit (prevents the player from hitting themself)
										continue;
									}
								}
								lEntity.damage(itemDamage, p);
								// Apply knockback
								lEntity.setVelocity(brock.getVelocity());
								brock.remove();
								cancel();
							}
						}
					}
				}.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0, 0);
			}

		}
		
	}

}
