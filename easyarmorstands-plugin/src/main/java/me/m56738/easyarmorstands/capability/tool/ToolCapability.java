package me.m56738.easyarmorstands.capability.tool;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Capability(name = "Tool detection", optional = true)
public interface ToolCapability {
    boolean isTool(ItemStack item);

    void configureTool(ItemMeta meta);
}
