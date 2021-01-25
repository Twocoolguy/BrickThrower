package me.TurtlesAreHot.BrickThrower;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.commands.BrickThrowerCommand;
import me.TurtlesAreHot.BrickThrower.events.PlayerClickEvent;
import me.TurtlesAreHot.BrickThrower.events.PrepareCraftEvent;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig(); // Creates config.yml
		FileConfiguration config = this.getConfig();
		config.addDefault("bricks-given", 10);
		config.addDefault("reload-enabled", true);
		config.addDefault("item-name", "Heavy Brick");
		List<String> materials = new ArrayList<String>();
		materials.add("BRICK");
		materials.add("NETHER_BRICK");
		config.addDefault("items", materials); // This list contains all of the materials that you can get from /brickthrower get.
		config.addDefault("default-item", "BRICK"); // this is the default item that /brickthrower get will give.
		config.addDefault("item-disappear-time", 2); // time until the item on the ground disappears. Put 0 to disable and allow pickup of the item.
		
		config.options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new PlayerClickEvent(),  this); // Adding event listener for any listener in the Main class.
		this.getServer().getPluginManager().registerEvents(new PrepareCraftEvent(), this);
		getCommand("brickthrower").setExecutor(new BrickThrowerCommand());
		Config.reloadConfig(); // Sets up our configreader object in our Config class.
		if(Config.oldServer()) {
			this.getServer().getLogger().warning("This server is on 1.12 or below. You will only be able to throw the brick material because of ID changes in those versions. We do not yet have support for this.");
		}
	}
	
	@Override
	public void onDisable() {

	}
}
