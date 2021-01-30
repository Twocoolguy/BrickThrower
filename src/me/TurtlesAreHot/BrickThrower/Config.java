package me.TurtlesAreHot.BrickThrower;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	
	private static FileConfiguration config;
	private static String version;
	
	public static void reloadConfig() {
		config = JavaPlugin.getPlugin(Main.class).getConfig();
		String server_ver = Bukkit.getVersion();
		String non_fixed_version = Bukkit.getServer().getBukkitVersion().split("-")[0];
		non_fixed_version.replaceAll("_", ".");
		version = non_fixed_version;
		String ver_fix = version.substring(0, version.lastIndexOf("."));
		if(!(ver_fix.equals("1"))) {
			//This is in case the version is like "1.12" or "1.8" etc
			version = ver_fix;
		}
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
	
	public static String getServerVersion() {
		return version;
	}
	
	public static int getDisappearTime() {
		return config.getInt("item-disappear-time");
	}

	public static boolean oldServer() {
		boolean result = false;
		switch(version) {
			case "1.12":
				result = true;
				break;
			case "1.11":
				result = true;
				break;
			case "1.10":
				result = true;
				break;
			case "1.9":
				result = true;
				break;
			case "1.8":
				result = true;
				break;
			default:
				result = false;
		}
		return result;
	}

	public static boolean twelveAndAbove() {
		boolean result = true;
		switch(version) {
			case "1.11":
				result = false;
				break;
			case "1.10":
				result = false;
				break;
			case "1.9":
				result = false;
				break;
			case "1.8":
				result = false;
				break;
			default:
				result = true;
		}

		return result;

	}
}
