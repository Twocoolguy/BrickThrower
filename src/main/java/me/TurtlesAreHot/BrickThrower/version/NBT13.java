package me.TurtlesAreHot.BrickThrower.version;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.plugin.java.JavaPlugin;

import me.TurtlesAreHot.BrickThrower.Main;

public class NBT13 {

	public static ItemStack setNBTData(ItemStack item, String key, String key_data) {
		ItemStack itemNBT = item;
		NamespacedKey objKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), key);
		ItemMeta itemMeta = itemNBT.getItemMeta();
		itemMeta.getCustomTagContainer().setCustomTag(objKey, ItemTagType.STRING, key_data);
		itemNBT.setItemMeta(itemMeta);
		return itemNBT;
	}

	public static String getNBTDataString(ItemStack item, String key) {
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
