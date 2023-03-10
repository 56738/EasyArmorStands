package me.m56738.easyarmorstands.capability.item;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;

@Capability(name = "Items")
public interface ItemCapability {
    ItemStack createItem(ItemType type);

    ItemStack createColor(DyeColor color);
}
