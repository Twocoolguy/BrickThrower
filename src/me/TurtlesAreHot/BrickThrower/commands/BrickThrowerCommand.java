package me.TurtlesAreHot.BrickThrower.commands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Main;
import net.md_5.bungee.api.ChatColor;

public class BrickThrowerCommand implements CommandExecutor {
	public boolean hasPermissionMessage(Player player, String permission) {
		// This function checks if the player has permission and returns true if they do and false if they do not.
		// If the player does not have permission, then it will print the permission node needed to use the command.
		boolean perm = player.hasPermission(permission);
		if (player.hasPermission("brickthrower.*")) {
			perm = true;
		}
		if (perm == false) {
			player.sendMessage(ChatColor.GOLD + "You must have the permission node: " + ChatColor.RED + permission + ChatColor.GOLD + " to perform this command. Contact an administrator to get this fixed!");
		}
		return perm;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			// Checks if the sender is not a player because only in-game players can use this plugin.
			System.out.println(ChatColor.DARK_RED + "You cannot use this plugin in console!");
			return false;
		}
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("brickthrower")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("get")) {
					if (hasPermissionMessage(player, "brickthrower.get")) {
						// Create an itemstack of bricks, giving it the name of "Heavy Brick" then giving it to the player.
						ItemStack heavyBrick = new ItemStack(Material.BRICK, JavaPlugin.getPlugin(Main.class).getConfig().getInt("bricks-given"));
						ItemMeta im = heavyBrick.getItemMeta(); 
						im.setDisplayName("Heavy Brick");
						im.setLore(Arrays.asList(getInvisString("no_craft")));
						heavyBrick.setItemMeta(im);
						player.getInventory().addItem(heavyBrick);
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload")) {
					if (hasPermissionMessage(player, "brickthrower.reload")) {
						if(JavaPlugin.getPlugin(Main.class).getConfig().getBoolean("reload-enabled")) {
							// Reloads the config data.
							player.sendMessage(ChatColor.GOLD + "BrickThrower has been reloaded!");
							JavaPlugin.getPlugin(Main.class).reloadConfig();
						}
						else {
							player.sendMessage(ChatColor.GOLD + "Reloading was disabled in the config.");
						}
					}
					return true;
				}
			}
			if (hasPermissionMessage(player, "brickthrower.info")) {
				// The following pulls information from the plugin.yml for this plugin.
				PluginDescriptionFile pdf = JavaPlugin.getPlugin(Main.class).getDescription();
				player.sendMessage(ChatColor.GOLD + "BrickThrower " + ChatColor.RED + "v" + pdf.getVersion());
				player.sendMessage(ChatColor.GOLD + "Created By: " + ChatColor.RED + "" + pdf.getAuthors().get(0));
				player.sendMessage(ChatColor.RED + "" + pdf.getDescription());
				player.sendMessage(ChatColor.GOLD + "Commands:");
				player.sendMessage(ChatColor.RED + "/brickthrower" + ChatColor.GOLD + " - This command displays information about the plugin and what each command does.");
				player.sendMessage(ChatColor.RED + "/brickthrower get" + ChatColor.GOLD + " - This command gives you bricks that you can throw.");
				player.sendMessage(ChatColor.RED + "/brickthrower reload" + ChatColor.GOLD + " - This command reloads the config.");
			}
		}
		return false;
	}
	
	
	public static String getInvisString(String normal) {
		String hidden = "";
		for (char c : normal.toCharArray()) {
			hidden += ChatColor.COLOR_CHAR + "" + c;
		}
		return hidden;
	}
}
