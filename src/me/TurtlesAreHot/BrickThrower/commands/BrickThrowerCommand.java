package me.TurtlesAreHot.BrickThrower.commands;

import java.util.ArrayList;
import java.util.List;

import me.TurtlesAreHot.BrickThrower.version.*;
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
import me.TurtlesAreHot.BrickThrower.Main;
import net.md_5.bungee.api.ChatColor;

import static org.bukkit.Material.getMaterial;


public class BrickThrowerCommand implements CommandExecutor {
	public boolean hasPermissionMessage(Player player, String permission) {
		// This function checks if the player has permission and returns true if they do and false if they do not.
		// If the player does not have permission, then it will print the permission node needed to use the command.
		boolean perm = player.hasPermission(permission);
		if (player.hasPermission("brickthrower.*")) {
			perm = true;
		}
		if (perm == false) {
			msgPlayer(player, "You must have the permission node: " + ChatColor.RED + permission + ChatColor.GOLD + " to perform this command. Contact an administrator to get this fixed!");
		}
		return perm;
	}
	
	public void msgPlayer(Player p, String message) {
		p.sendMessage(ChatColor.GOLD + "[BrickThrower] " + message);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			// Checks if the sender is not a player because only in-game players can use this plugin.
			sender.getServer().getLogger().info("You cannot use this plugin in console!");
			return false;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("brickthrower") || label.equalsIgnoreCase("brth")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("get")) {
					if (hasPermissionMessage(p, "brickthrower.get")) {
						// Create an itemstack of bricks, giving it the name of "Heavy Brick" then giving it to the player.
						Material default_item = Config.getDefaultItem();
						if(default_item == null) {
							msgPlayer(p, "BrickThrower has encountered an error: " + ChatColor.RED + "The config option for 'default-item' is set to an invalid item. Please change it to a valid item. The item type will be set to bricks because of this.");
							default_item = Material.BRICK;
						}
						ItemStack heavyBrick = new ItemStack(default_item, Config.getBricksGiven());
						if(Config.oldServer()) {
							heavyBrick.setType(getMaterial("CLAY_BRICK"));
							if(args.length > 1) {
								msgPlayer(p, "Versions 1.12 and below only support the material clay brick. Sorry.");
							}
						}
						else {
							if (args.length > 1) {
								if (hasPermissionMessage(p, "brickthrower.getother")) {
									List<String> items = Config.getMaterialList();
									String param = args[1];
									if (items.contains(param.toUpperCase())) {
										Material fixed_item = getMaterial(param.toUpperCase());
										if (fixed_item == null) {
											msgPlayer(p, "The item that you gave was not a valid item in Minecraft. Please choose a different item.");
											return false;
										} else {
											heavyBrick.setType(fixed_item);
										}
									} else {
										msgPlayer(p, "This is not a valid item listed. Please choose a valid one.");
										return false;
									}
								} else {
									return false;
								}
							}
						}
						if(!(Config.oldServer()) && (heavyBrick.getType().isBlock() || heavyBrick.getType().isEdible())) {
							// Checks if the item is placeable/edible.
							msgPlayer(p, "The item that is trying to be used is placeable. You cannot throw placeable/edible items.");
							return false;
						}
						ItemMeta im = heavyBrick.getItemMeta(); 
						im.setDisplayName(Config.getItemName());
 						heavyBrick.setItemMeta(im);
 						String version = Config.getServerVersion();
 						switch(version) {
							case "1.13":
								heavyBrick = NBT13.setNBTData(heavyBrick, "brickthrower_item", "true");
								break;
							case "1.12":
								heavyBrick = NBT12.setNBTData(heavyBrick, "brickthrower_item", "true");
								break;
							case "1.11":
								heavyBrick = NBT11.setNBTData(heavyBrick, "brickthrower_item", "true");
								break;
							case "1.10":
								heavyBrick = NBT10.setNBTData(heavyBrick, "brickthrower_item", "true");
								break;
							case "1.9":
								heavyBrick = NBT9.setNBTData(heavyBrick, "brickthrower_item", "true");
								break;
							case "1.8":
								heavyBrick = NBT8.setNBTData(heavyBrick, "brickthrower_item", "true");
								break;
							default:
								heavyBrick = NBT14.setNBTData(heavyBrick, "brickthrower_item", "true");
						}
 						p.getInventory().addItem(heavyBrick);
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload")) {
					if (hasPermissionMessage(p, "brickthrower.reload")) {
						if(Config.isReloadEnabled()) {
							// Reloads the config data.
							msgPlayer(p, "BrickThrower has been reloaded!");
							JavaPlugin.getPlugin(Main.class).reloadConfig();
							Config.reloadConfig();
						}
						else {
							msgPlayer(p, "Reloading was disabled in the config. A server restart is required to change the config.");
						}
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("list")) {
					if(Config.oldServer()) {
						msgPlayer(p, "There is no extra materials you can choose from on 1.12 and below.");
						return false;
					}
					if (hasPermissionMessage(p, "brickthrower.list")) {
						List<String> materials = Config.getMaterialList();
						msgPlayer(p, "Valid Materials:");
						for (String mat : materials) {
							p.sendMessage(ChatColor.RED + mat); // This message send remains the same because we don't want to add "[BrickThrower]" every time.
						}
					}
					else {
						return false;
					}
					return true;
				}
				else if (args[0].equalsIgnoreCase("checkconfig")) {
					if(Config.oldServer()) {
						msgPlayer(p, "Any extra items do not work on 1.12 and below, so there is no point in checking if they are valid.");
						return false;
					}
					if (hasPermissionMessage(p, "brickthrower.checkconfig")) {
						List<String> materials = Config.getMaterialList();
						Material default_material = Config.getDefaultItem();
						List<String> errors = new ArrayList<String>();
						if(default_material == null) {
							errors.add(ChatColor.GOLD + "The default-item material is an invalid material.");
						}
						else {
							if(default_material.isBlock() || default_material.isEdible()) {
								errors.add(ChatColor.GOLD + "The default-item material " + ChatColor.RED + default_material.toString() + ChatColor.GOLD + " is an invalid material because it can be placed or is edible.");
							}
						}
						for (String mat : materials) {
							Material item_mat = getMaterial(mat);
							if(item_mat == null) {
								errors.add(ChatColor.GOLD + "The item material " + ChatColor.RED + mat + ChatColor.GOLD + " is an invalid material.");
							}
							else {
								if(item_mat.isBlock() || item_mat.isEdible()) {
									errors.add(ChatColor.GOLD + "The item material " + ChatColor.RED + mat + ChatColor.GOLD + " is an invalid material because it can be placed or is edible.");
								}
							}
						}
						if(errors.size() > 0) {
							msgPlayer(p, ChatColor.DARK_RED + "Errors:");
							for (String err : errors) {
								p.sendMessage(err); // Again do not want to add "[BrickThrower] every time.
							}
						}
						else {
							msgPlayer(p, "There was no errors!");
						}
						
					}
					else {
						return false;
					}
					return true;
				}
			}
			if (hasPermissionMessage(p, "brickthrower.info")) {
				// The following pulls information from the plugin.yml for this plugin.
				PluginDescriptionFile pdf = JavaPlugin.getPlugin(Main.class).getDescription();
				// All of the messages sent here don't use the function because we don't want "[BrickThrower]" every single time.
				p.sendMessage(ChatColor.GOLD + "BrickThrower " + ChatColor.RED + "v" + pdf.getVersion());
				p.sendMessage(ChatColor.GOLD + "Created By: " + ChatColor.RED + "" + pdf.getAuthors().get(0));
				p.sendMessage(ChatColor.RED + "" + pdf.getDescription());
				p.sendMessage(ChatColor.GOLD + "Commands:");
				p.sendMessage(ChatColor.DARK_RED + "[] <- optional parameter <> <- required parameter");
				p.sendMessage(ChatColor.RED + "/brickthrower" + ChatColor.GOLD + " - This command displays information about the plugin and what each command does.");
				p.sendMessage(ChatColor.RED + "/brickthrower get [material]" + ChatColor.GOLD + " - This command gives you bricks that you can throw.");
				p.sendMessage(ChatColor.RED + "/brickthrower reload" + ChatColor.GOLD + " - This command reloads the config.");
				p.sendMessage(ChatColor.RED + "/brickthrower list" + ChatColor.GOLD + " - This command lists all of the materials you can use with the /brickthrower get command.");
				p.sendMessage(ChatColor.RED + "/brickthrower checkconfig" + ChatColor.GOLD + " - This command goes through the items list and default-item in the config and tells you if there are invalid material types.");
			}
		}
		return false;
	}
}
