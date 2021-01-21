package me.TurtlesAreHot.BrickThrower.version;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Main;

public class NBT14 {
	//Works on 1.14+

	public static ItemStack setNBTData(ItemStack item, String key, String key_data) {
		ItemStack itemNBT = item;
		NamespacedKey objKey =  new NamespacedKey(JavaPlugin.getPlugin(Main.class), key);
		ItemMeta itemMeta = itemNBT.getItemMeta();
		itemMeta.getPersistentDataContainer().set(objKey, PersistentDataType.STRING, key_data);
		itemNBT.setItemMeta(itemMeta);
		return itemNBT;
	}

	public static String getNBTDataString(ItemStack item, String key) {
		NamespacedKey objKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), key);
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) {
			return null;
		}
		PersistentDataContainer container = (PersistentDataContainer) itemMeta.getPersistentDataContainer();
		if(container.has(objKey, PersistentDataType.STRING)) {
			return container.get(objKey, PersistentDataType.STRING);
		}
		return null;

	}

}
