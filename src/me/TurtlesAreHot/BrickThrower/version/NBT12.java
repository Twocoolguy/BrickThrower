package me.TurtlesAreHot.BrickThrower.version;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBT12 {
    // Hopefully works for 1.12 and below...

    public static String getNBTDataString(ItemStack item, String key) {
        ItemStack nmsItem = item;
        net.minecraft.server.v1_15_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(nmsItem);
        NBTTagCompound itemCompound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return itemCompound.getString(key);
    }

    public static ItemStack setNBTData(ItemStack item, String key, String key_data) {
        ItemStack nmsItem = item;
        net.minecraft.server.v1_15_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(nmsItem);
        NBTTagCompound itemCompound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        itemCompound.set(key, NBTTagString.a(key_data));
        nmsStack.setTag(itemCompound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }
}
