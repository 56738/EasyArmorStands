package me.m56738.easyarmorstands.capability.item.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ItemCapabilityProvider implements CapabilityProvider<ItemCapability> {
    @Override
    public boolean isSupported() {
        try {
            Material.valueOf("STEP");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public ItemCapability create(Plugin plugin) {
        return new ItemCapabilityImpl();
    }

    private static class ItemCapabilityImpl implements ItemCapability {
        @Override
        @SuppressWarnings("deprecation")
        public ItemStack createColor(DyeColor color) {
            return new ItemStack(Material.WOOL, 1, color.getWoolData());
        }
    }
}
