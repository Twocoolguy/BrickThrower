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
		// startup
		// reloads
		// plugin reloads
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
		config.options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new PlayerClickEvent(),  this); // Adding event listener for any listener in the Main class.
		this.getServer().getPluginManager().registerEvents(new PrepareCraftEvent(), this);
		getCommand("brickthrower").setExecutor(new BrickThrowerCommand());
		Config.reloadConfig(); // This "reloads" the config. As in it resets the values that it has to the values that the config currently contains.
	}
	
	@Override
	public void onDisable() {
		// shutdown
		// reloads
		// plugin reloads
	}
}
