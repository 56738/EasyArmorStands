package me.m56738.easyarmorstands.capability.itemcolor.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

public class ItemColorCapabilityProvider implements CapabilityProvider<ItemColorCapability> {
    @Override
    public boolean isSupported() {
        try {
            LeatherArmorMeta.class.getMethod("getColor");
            LeatherArmorMeta.class.getMethod("setColor", Color.class);
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
    public ItemColorCapability create(Plugin plugin) {
        return new ItemColorCapabilityImpl();
    }

    private static class ItemColorCapabilityImpl implements ItemColorCapability {
        @Override
        public boolean hasColor(ItemMeta meta) {
            return meta instanceof LeatherArmorMeta;
        }

        @Override
        public Color getColor(ItemMeta meta) {
            if (meta instanceof LeatherArmorMeta) {
                return ((LeatherArmorMeta) meta).getColor();
            }
            return null;
        }

        @Override
        public boolean setColor(ItemMeta meta, Color color) {
            if (meta instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta) meta).setColor(color);
                return true;
            }
            return false;
        }
    }
}
