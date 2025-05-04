package me.m56738.easyarmorstands.capability.tool;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.inventory.ItemStack;

@Capability(name = "Tool detection", optional = true)
public interface ToolCapability {
    boolean isTool(ItemStack item);

    void configureTool(ItemStack item);
}
