package me.m56738.easyarmorstands.modded.color;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.util.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.references.ItemIds;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.MapItemColor;

import java.util.List;

public class ModdedColorPickerContext implements ColorPickerContext {
    private static final List<ItemColorAccessor> ACCESSORS = List.of(
            new DataItemColorAccessor<>(
                    DataComponents.DYED_COLOR,
                    new DyedItemColor(DyedItemColor.LEATHER_COLOR),
                    h -> h.is(ItemIds.LEATHER_HELMET)
                            || h.is(ItemIds.LEATHER_CHESTPLATE)
                            || h.is(ItemIds.LEATHER_LEGGINGS)
                            || h.is(ItemIds.LEATHER_BOOTS)
                            || h.is(ItemIds.LEATHER_HORSE_ARMOR),
                    DyedItemColor::new,
                    DyedItemColor::rgb),
            new DataItemColorAccessor<>(
                    DataComponents.MAP_COLOR,
                    MapItemColor.DEFAULT,
                    h -> h.is(ItemIds.FILLED_MAP),
                    MapItemColor::new,
                    MapItemColor::rgb));

    private final ModdedPlatform platform;
    private final Property<ItemStack> property;

    public ModdedColorPickerContext(ModdedPlatform platform, Property<ItemStack> property) {
        this.platform = platform;
        this.property = property;
    }

    @Override
    public Platform platform() {
        return platform;
    }

    @Override
    public ItemStack item() {
        return property.getValue();
    }

    public static boolean isSupported(ItemStack item) {
        return isSupported(ModdedItemStack.toNative(item));
    }

    private static boolean isSupported(net.minecraft.world.item.ItemStack item) {
        for (ItemColorAccessor accessor : ACCESSORS) {
            if (accessor.isSupported(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RGBColor getColor() {
        net.minecraft.world.item.ItemStack item = ModdedItemStack.toNative(property.getValue());
        for (ItemColorAccessor accessor : ACCESSORS) {
            if (accessor.isSupported(item)) {
                return accessor.getColor(item);
            }
        }
        return RGBColor.WHITE;
    }

    @Override
    public void setColor(RGBColor color) {
        net.minecraft.world.item.ItemStack item = ModdedItemStack.toNative(property.getValue());
        for (ItemColorAccessor accessor : ACCESSORS) {
            if (accessor.isSupported(item)) {
                accessor.setColor(item, color);
                property.setValue(ModdedItemStack.fromNative(platform, item));
                property.commit(Message.component("easyarmorstands.history.changed-color", Util.formatColor(color)));
                return;
            }
        }
    }
}
