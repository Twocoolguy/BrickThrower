package me.TurtlesAreHot.BrickThrower.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Config;
import me.TurtlesAreHot.BrickThrower.GeneralMethods;
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
			sender.getServer().getLogger().info(ChatColor.DARK_RED + "You cannot use this plugin in console!");
			return false;
		}
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("brickthrower") || label.equalsIgnoreCase("brth")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("get")) {
					if (hasPermissionMessage(player, "brickthrower.get")) {
						// Create an itemstack of bricks, giving it the name of "Heavy Brick" then giving it to the player.
						Material default_item = Config.getDefaultItem();
						if(default_item == null) {
							player.sendMessage(ChatColor.GOLD + "BrickThrower has encountered an error: " + ChatColor.RED + "The config option for 'default-item' is set to an invalid item. Please change it to a valid item. The item type will be set to bricks because of this.");
							default_item = Material.BRICK;
						}
						ItemStack heavyBrick = new ItemStack(default_item, Config.getBricksGiven());
						
						if (args.length > 1) {
							if(hasPermissionMessage(player, "brickthrower.getother")) {
								List<String> items = Config.getMaterialList();
								String param = args[1];
								if(items.contains(param)) {
									Material fixed_item = Material.getMaterial(param);
									if(fixed_item == null) {
										player.sendMessage(ChatColor.GOLD + "The item that you gave was not a valid item in Minecraft. Please choose a different item.");
										return false;
									}
									else {
										heavyBrick.setType(fixed_item);
									}
								}
								else {
									player.sendMessage(ChatColor.GOLD + "This is not a valid item listed. Please choose a valid one.");
									return false;
								}
							}
							else { return false; }
						}
						if(heavyBrick.getType().isBlock()) {
							// Checks if the item is placeable.
							player.sendMessage(ChatColor.GOLD + "The item that is trying to be used is placeable. You cannot throw placeable items.");
							return false;
						}
						ItemMeta im = heavyBrick.getItemMeta(); 
						im.setDisplayName(Config.getItemName());
 						heavyBrick.setItemMeta(im);
 						String server_ver = Bukkit.getVersion();
 						String version = server_ver.substring(server_ver.indexOf("(MC: ")+5, server_ver.indexOf("(MC: ")+9);
 						if (version.equalsIgnoreCase("1.13")) {
 							heavyBrick = GeneralMethods.setNBTTag13(heavyBrick, "brickthrower_item", "true");
 						}
 						else {
 							heavyBrick = GeneralMethods.setNBTTag(heavyBrick, "brickthrower_item", "true");
 						}
 						player.getInventory().addItem(heavyBrick);
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload")) {
					if (hasPermissionMessage(player, "brickthrower.reload")) {
						if(Config.isReloadEnabled()) {
							// Reloads the config data.
							player.sendMessage(ChatColor.GOLD + "BrickThrower has been reloaded!");
							JavaPlugin.getPlugin(Main.class).reloadConfig();
							Config.reloadConfig();
						}
						else {
							player.sendMessage(ChatColor.GOLD + "Reloading was disabled in the config. A server restart is required to change the config.");
						}
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("list")) {
					if (hasPermissionMessage(player, "brickthrower.list")) {
						List<String> materials = Config.getMaterialList();
						player.sendMessage(ChatColor.GOLD + "Valid Materials:");
						for (String mat : materials) {
							player.sendMessage(ChatColor.RED + mat);
						}
					}
					else {
						return false;
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("checkconfig")) {
					if (hasPermissionMessage(player, "brickthrower.checkconfig")) {
						List<String> materials = Config.getMaterialList();
						Material default_material = Config.getDefaultItem();
						List<String> errors = new ArrayList<String>();
						if(default_material == null) {
							errors.add(ChatColor.GOLD + "The default-item material is an invalid material.");
						}
						else {
							if(default_material.isBlock()) {
								errors.add(ChatColor.GOLD + "The default-item material " + ChatColor.RED + default_material.toString() + ChatColor.GOLD + " is an invalid material because it can be placed.");
							}
						}
						for (String mat : materials) {
							Material item_mat = Material.getMaterial(mat);
							if(item_mat == null) {
								errors.add(ChatColor.GOLD + "The item material " + ChatColor.RED + mat + ChatColor.GOLD + " is an invalid material.");
							}
							else {
								if(item_mat.isBlock()) {
									errors.add(ChatColor.GOLD + "The item material " + ChatColor.RED + mat + ChatColor.GOLD + " is an invalid material because it can be placed.");
								}
							}
						}
						if(errors.size() > 0) {
							player.sendMessage(ChatColor.DARK_RED + "Errors:");
							for (String err : errors) {
								player.sendMessage(err);
							}
						}
						else {
							player.sendMessage(ChatColor.GOLD + "There was no errors!");
						}
						
					}
					else {
						return false;
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
				player.sendMessage(ChatColor.DARK_RED + "[] <- optional parameter <> <- required parameter");
				player.sendMessage(ChatColor.RED + "/brickthrower" + ChatColor.GOLD + " - This command displays information about the plugin and what each command does.");
				player.sendMessage(ChatColor.RED + "/brickthrower get [material]" + ChatColor.GOLD + " - This command gives you bricks that you can throw.");
				player.sendMessage(ChatColor.RED + "/brickthrower reload" + ChatColor.GOLD + " - This command reloads the config.");
				player.sendMessage(ChatColor.RED + "/brickthrower list" + ChatColor.GOLD + " - This command lists all of the materials you can use with the /brickthrower get command.");
				player.sendMessage(ChatColor.RED + "/brickthrower checkconfig" + ChatColor.GOLD + " - This command goes through the items list and default-item in the config and tells you if there are invalid material types.");
			}
		}
		return false;
	}
}
