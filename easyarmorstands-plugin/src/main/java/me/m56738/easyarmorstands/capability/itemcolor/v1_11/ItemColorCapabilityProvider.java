package me.m56738.easyarmorstands.capability.itemcolor.v1_11;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.Plugin;

public class ItemColorCapabilityProvider implements CapabilityProvider<ItemColorCapability> {
    @Override
    public boolean isSupported() {
        try {
            LeatherArmorMeta.class.getMethod("getColor");
            LeatherArmorMeta.class.getMethod("setColor", Color.class);
            MapMeta.class.getMethod("getColor");
            MapMeta.class.getMethod("setColor", Color.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public ItemColorCapability create(Plugin plugin) {
        return new ItemColorCapabilityImpl();
    }

    private static class ItemColorCapabilityImpl implements ItemColorCapability {
        @Override
        public boolean hasColor(ItemMeta meta) {
            return meta instanceof LeatherArmorMeta || meta instanceof MapMeta;
        }

        @Override
        public Color getColor(ItemMeta meta) {
            if (meta instanceof LeatherArmorMeta) {
                return ((LeatherArmorMeta) meta).getColor();
            }
            if (meta instanceof MapMeta) {
                MapMeta mapMeta = (MapMeta) meta;
                if (!mapMeta.hasColor()) {
                    return Color.fromRGB(0x46402E);
                }
                return mapMeta.getColor();
            }
            return null;
        }

        @Override
        public boolean setColor(ItemMeta meta, Color color) {
            if (meta instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta) meta).setColor(color);
                return true;
            }
            if (meta instanceof MapMeta) {
                ((MapMeta) meta).setColor(color);
                return true;
            }
            return false;
        }
    }
}
