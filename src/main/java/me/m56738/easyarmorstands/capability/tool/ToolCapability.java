package me.m56738.easyarmorstands.capability.tool;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.inventory.ItemStack;

@Capability(name = "Tool")
public interface ToolCapability {
    boolean isTool(ItemStack item);

    ItemStack createTool();
}
