package me.TurtlesAreHot.BrickThrower;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	
	private static FileConfiguration config;
	
	public static void reloadConfig() {
		config = JavaPlugin.getPlugin(Main.class).getConfig();
	}
	
	public static Material getDefaultItem() {
		return Material.getMaterial(config.getString("default-item"));
	}
	
	public static List<String> getMaterialList() {
		return config.getStringList("items");
	}
	
	public static int getBricksGiven() {
		return config.getInt("bricks-given");
	}
	
	public static boolean isReloadEnabled() {
		return config.getBoolean("reload-enabled");
	}
	
	public static String getItemName() {
		return config.getString("item-name");
	}
}
