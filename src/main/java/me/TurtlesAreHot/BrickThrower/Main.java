package me.TurtlesAreHot.BrickThrower;

import me.TurtlesAreHot.BrickThrower.commands.BrickThrower;
import me.TurtlesAreHot.BrickThrower.version.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static FileConfiguration config;
    private static String version;

    @Override
    public void onEnable() {
        reloadVersion();
        setDefaultConfigs();

        getCommand("brickthrower").setExecutor(new BrickThrower());
    }

    @Override
    public void onDisable() {

    }

    public static void reloadCon() {
        config = JavaPlugin.getPlugin(Main.class).getConfig();
    }

    public void reloadVersion() {
        // garbage....
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

    // Gets config file
    public static FileConfiguration getCon() {
        return config;
    }

    // Gets Server version
    public String getServerVersion() {
        return version;
    }

    public void setDefaultConfigs() {
        this.saveDefaultConfig(); // Creates config.yml if it doesn't exist.
        config = this.getConfig();
        config.addDefault("requires-command", true);
        config.addDefault("bricks-given", 10);
        config.addDefault("reload-enabled", true);
        config.addDefault("item-name", "Heavy Brick");
        List<String> materials = new ArrayList<String>();
        String default_item_mat = "BRICK";

        // Checking for versions 1.12 and below
        if(oldMaterials()) {
            // Version 1.12 or below
            materials.add("CLAY_BRICK");
            materials.add("NETHER_BRICK_ITEM");
            default_item_mat = "CLAY_BRICK";
        }
        else {
            materials.add("BRICK");
            materials.add("NETHER_BRICK");
        }
        config.addDefault("items", materials); // This list contains all of the materials that you can get from /brickthrower get.
        config.addDefault("default-item", default_item_mat); // this is the default item that /brickthrower get will give.
        config.addDefault("item-disappear-time", 2); // time until the item on the ground disappears. Put 0 to disable and allow pickup of the item.
        config.addDefault("item-damage", 0.0D);
        config.addDefault("allow-interacts", false);
        config.addDefault("allow-guis", false);
        config.addDefault("item-velocity-multiplier", 1.0D);
        config.addDefault("kb-velocity-multiplier", 1.0D);


    }

    public static boolean oldMaterials() {
        String endOfVersion = version.substring(version.indexOf('.') + 1);
        int verNum = 0;
        try {
            verNum = Integer.parseInt(endOfVersion);
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }

        if(verNum <= 12) {
            return true;
        }

        return false;
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

    public static ItemStack setNBTData(ItemStack item, String key, String keyData) {
        ItemStack data = null;
        switch(version) {
            case "1.13":
                data = NBT13.setNBTData(item, key, keyData);
                break;
            case "1.12":
                data = NBT12.setNBTData(item, key, keyData);
                break;
            case "1.11":
                data = NBT11.setNBTData(item, key, keyData);
                break;
            case "1.10":
                data = NBT10.setNBTData(item, key, keyData);
                break;
            case "1.9":
                data = NBT9.setNBTData(item, key, keyData);
                break;
            case "1.8":
                data = NBT8.setNBTData(item, key, keyData);
                break;
            default:
                data = NBT14.setNBTData(item, key, keyData);
        }
        return data;
    }


}
