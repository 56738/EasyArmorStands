package me.m56738.easyarmorstands.capability.item.v1_13;

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
            Material.valueOf("LIGHT_BLUE_STAINED_GLASS_PANE");
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
    public ItemCapability create(Plugin plugin) {
        return new ItemCapabilityImpl();
    }

    private static class ItemCapabilityImpl implements ItemCapability {
        @Override
        public ItemStack createColor(DyeColor color) {
            switch (color) {
                case WHITE:
                    return new ItemStack(Material.WHITE_CONCRETE);
                case ORANGE:
                    return new ItemStack(Material.ORANGE_CONCRETE);
                case MAGENTA:
                    return new ItemStack(Material.MAGENTA_CONCRETE);
                case LIGHT_BLUE:
                    return new ItemStack(Material.LIGHT_BLUE_CONCRETE);
                case YELLOW:
                    return new ItemStack(Material.YELLOW_CONCRETE);
                case LIME:
                    return new ItemStack(Material.LIME_CONCRETE);
                case PINK:
                    return new ItemStack(Material.PINK_CONCRETE);
                case GRAY:
                    return new ItemStack(Material.GRAY_CONCRETE);
                case LIGHT_GRAY:
                    return new ItemStack(Material.LIGHT_GRAY_CONCRETE);
                case CYAN:
                    return new ItemStack(Material.CYAN_CONCRETE);
                case PURPLE:
                    return new ItemStack(Material.PURPLE_CONCRETE);
                case BLUE:
                    return new ItemStack(Material.BLUE_CONCRETE);
                case BROWN:
                    return new ItemStack(Material.BROWN_CONCRETE);
                case GREEN:
                    return new ItemStack(Material.GREEN_CONCRETE);
                case RED:
                    return new ItemStack(Material.RED_CONCRETE);
                case BLACK:
                    return new ItemStack(Material.BLACK_CONCRETE);
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
