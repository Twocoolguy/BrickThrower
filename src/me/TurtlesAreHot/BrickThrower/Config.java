package me.TurtlesAreHot.BrickThrower;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import me.TurtlesAreHot.BrickThrower.version.*;

public class Config {
	
	private static FileConfiguration config;
	private static String version;
	
	public static void reloadConfig() {
		config = JavaPlugin.getPlugin(Main.class).getConfig();
		reloadVersion();
	}

	public static void reloadVersion() {
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

	public static double getItemDamage() { return config.getDouble("item-damage"); }

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
	public static boolean sixteenAndAbove() {
		boolean result = true;
		switch(version) {
			case "1.15":
				result = false;
				break;
			case "1.14":
				result = false;
				break;
			case "1.13":
				result = false;
				break;
			case "1.12":
				result = false;
				break;
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
	public static boolean fourteenAndAbove() {
		boolean result = true;
		switch(version) {
			case "1.13":
				result = false;
				break;
			case "1.12":
				result = false;
				break;
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

	public static String getNBTData(ItemStack item, String key) {
		String data = null;
		switch(version) {
			case "1.13":
				data = NBT13.getNBTDataString(item, key);
				break;
			case "1.12":
				data = NBT12.getNBTDataString(item, key);
				break;
			case "1.11":
				data = NBT11.getNBTDataString(item, key);
				break;
			case "1.10":
				data = NBT10.getNBTDataString(item, key);
				break;
			case "1.9":
				data = NBT9.getNBTDataString(item, key);
				break;
			case "1.8":
				data = NBT8.getNBTDataString(item, key);
				break;
			default:
				data = NBT14.getNBTDataString(item, key);
		}
		return data;
	}
}
