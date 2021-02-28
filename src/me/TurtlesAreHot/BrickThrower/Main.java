package me.TurtlesAreHot.BrickThrower;

import java.util.List;
import java.util.ArrayList;

import me.TurtlesAreHot.BrickThrower.events.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.commands.BrickThrowerCommand;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Config.reloadVersion();
		this.saveDefaultConfig(); // Creates config.yml
		FileConfiguration config = this.getConfig();
		config.addDefault("bricks-given", 10);
		config.addDefault("reload-enabled", true);
		config.addDefault("item-name", "Heavy Brick");
		List<String> materials = new ArrayList<String>();
		String default_item_mat = "BRICK";
		if(Config.oldServer()) {
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
		config.addDefault("item-damage", 0.0);

		config.options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new PlayerClickEvent(),  this); // Adding event listener for any listener in the Main class.
		this.getServer().getPluginManager().registerEvents(new PrepareCraftEvent(), this);
		this.getServer().getPluginManager().registerEvents(new EnchantEvent(), this);
		this.getServer().getPluginManager().registerEvents(new FurnaceSmelt(), this);
		this.getServer().getPluginManager().registerEvents(new AnvilEvent(), this);
		if(Config.sixteenAndAbove()) {
			this.getServer().getPluginManager().registerEvents(new SmithingEvent(), this);
		}
		getCommand("brickthrower").setExecutor(new BrickThrowerCommand());
		Config.reloadConfig(); // Sets up our configreader object in our Config class.
	}
	
	@Override
	public void onDisable() {

	}
}
