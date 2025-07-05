package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColorPickerContextImpl implements ColorPickerContext {
    private final Property<ItemStack> property;
    private final PropertyContainer container;

    public ColorPickerContextImpl(Property<ItemStack> property, PropertyContainer container) {
        this.property = property;
        this.container = container;
    }

    public ColorPickerContextImpl(ItemPropertySlot slot) {
        this(slot.getProperty(), slot.getContainer());
    }

    @Override
    public @NotNull ItemStack item() {
        return property.getValue();
    }

    @Override
    public @NotNull Color getColor() {
        ItemStack item = property.getValue();
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            return leatherArmorMeta.getColor();
        }
        if (meta instanceof MapMeta mapMeta) {
            if (mapMeta.hasColor()) {
                return Objects.requireNonNull(mapMeta.getColor());
            } else {
                return Color.fromRGB(0x46402E);
            }
        }
        return Color.WHITE;
    }

    @Override
    public void setColor(@NotNull Color color) {
        ItemStack item = property.getValue().clone();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        if (setColor(meta, color)) {
            item.setItemMeta(meta);
            property.setValue(item);
            container.commit(Message.component("easyarmorstands.history.changed-color", Util.formatColor(color)));
        }
    }

    private boolean setColor(ItemMeta meta, Color color) {
        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            leatherArmorMeta.setColor(color);
            return true;
        }
        if (meta instanceof MapMeta mapMeta) {
            mapMeta.setColor(color);
            return true;
        }
        return false;
    }
}
