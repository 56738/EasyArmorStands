package me.m56738.easyarmorstands.capability.tool.v1_8;

import de.tr7zw.changeme.nbtapi.NBT;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class ToolCapabilityProvider implements CapabilityProvider<ToolCapability> {
    @Override
    public boolean isSupported() {
        return NBT.preloadApi();
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public ToolCapability create(Plugin plugin) {
        return new ToolCapabilityImpl();
    }

    private static class ToolCapabilityImpl implements ToolCapability {
        @Override
        public boolean isTool(ItemStack item) {
            if (item.getType() == Material.AIR || item.getAmount() <= 0) {
                return false;
            }
            return NBT.get(item, nbt -> {
                return Objects.equals(nbt.getBoolean("easyarmorstands_tool"), Boolean.TRUE);
            });
        }

        @Override
        public void configureTool(ItemStack item) {
            NBT.modify(item, nbt -> {
                nbt.setBoolean("easyarmorstands_tool", true);
            });
        }
    }
}
