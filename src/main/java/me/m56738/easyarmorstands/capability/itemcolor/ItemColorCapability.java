package me.m56738.easyarmorstands.capability.itemcolor;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;

@Capability(name = "Item colors")
public interface ItemColorCapability {
    boolean hasColor(ItemMeta meta);

    Color getColor(ItemMeta meta);

    boolean setColor(ItemMeta meta, Color color);
}
