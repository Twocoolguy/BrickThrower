package me.TurtlesAreHot.BrickThrower.version;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBT10 {
    public static String getNBTDataString(ItemStack item, String key) {
        ItemStack nmsItem = item;
        net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(nmsItem);
        if(nmsStack == null) {
            return null;
        }
        NBTTagCompound itemCompound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        String result = itemCompound.getString(key);
        if(result != null && !result.equals("true")) {
            return null;
        }
        return result;
    }

    public static ItemStack setNBTData(ItemStack item, String key, String key_data) {
        ItemStack nmsItem = item;
        net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(nmsItem);
        NBTTagCompound itemCompound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        itemCompound.set(key, new NBTTagString(key_data));
        nmsStack.setTag(itemCompound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }
}
