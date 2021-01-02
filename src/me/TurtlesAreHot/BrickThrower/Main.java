package me.TurtlesAreHot.BrickThrower;

import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.commands.BrickThrowerCommand;
import me.TurtlesAreHot.BrickThrower.events.PlayerClickEvent;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		// startup
		// reloads
		// plugin reloads
		this.saveDefaultConfig(); // Creates config.yml
		this.getConfig().addDefault("bricks-given", 10);
		this.getConfig().addDefault("reload-enabled", true);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new PlayerClickEvent(),  this); // Adding event listener for any listener in the Main class.
		getCommand("brickthrower").setExecutor(new BrickThrowerCommand());

	}
	
	@Override
	public void onDisable() {
		// shutdown
		// reloads
		// plugin reloads
	}
}
