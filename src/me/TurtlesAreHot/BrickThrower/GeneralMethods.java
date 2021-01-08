package me.TurtlesAreHot.BrickThrower;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class GeneralMethods {
	
	public static ItemStack setNBTTag(ItemStack item, String key, String key_data) {
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
	
	public static ItemStack setNBTTag13(ItemStack item, String key, String key_data) {
		ItemStack itemNBT = item;
		NamespacedKey objKey =  new NamespacedKey(JavaPlugin.getPlugin(Main.class), key);
		ItemMeta itemMeta = itemNBT.getItemMeta();
		itemMeta.getCustomTagContainer().setCustomTag(objKey, ItemTagType.STRING, key_data);
		itemNBT.setItemMeta(itemMeta);
		return itemNBT;
	}
	
	public static String getNBTDataString13(ItemStack item, String key) {
		NamespacedKey objKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), key);
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta == null) {
			return null;
		}
		CustomItemTagContainer tagContainer = itemMeta.getCustomTagContainer();
		
		if(tagContainer.hasCustomTag(objKey, ItemTagType.STRING)) {
			return tagContainer.getCustomTag(objKey, ItemTagType.STRING);
		}
		return null;
		
	}
}
