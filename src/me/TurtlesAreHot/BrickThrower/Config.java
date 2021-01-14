package me.TurtlesAreHot.BrickThrower;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	static Material default_item;
	static List<String> material_list;
	static int bricks_given;
	static boolean reload_enabled;
	static String item_name;
	
	public static void reloadConfig() {
		FileConfiguration config = JavaPlugin.getPlugin(Main.class).getConfig();
		default_item = Material.getMaterial(config.getString("default-item"));
		material_list = config.getStringList("items");
		bricks_given = config.getInt("bricks-given");
		reload_enabled = config.getBoolean("reload-enabled");
		item_name = config.getString("item-name");
	}
	
	public static Material getDefaultItem() {
		return default_item;
	}
	
	public static List<String> getMaterialList() {
		return material_list;
	}
	
	public static int getBricksGiven() {
		return bricks_given;
	}
	
	public static boolean isReloadEnabled() {
		return reload_enabled;
	}
	
	public static String getItemName() {
		return item_name;
	}
}
