package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerContextImpl implements ColorPickerContext {
    private final Property<ItemStack> property;
    private final PropertyContainer container;
    private final ItemColorCapability itemColorCapability;
    private final List<Runnable> subscriptions = new ArrayList<>();

    public ColorPickerContextImpl(Property<ItemStack> property, PropertyContainer container) {
        this.property = property;
        this.container = container;
        this.itemColorCapability = EasyArmorStandsPlugin.getInstance().getCapability(ItemColorCapability.class);
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
        if (item == null) {
            return Color.WHITE;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return Color.WHITE;
        }
        return itemColorCapability.getColor(meta);
    }

    @Override
    public void setColor(@NotNull Color color) {
        ItemStack item = property.getValue().clone();
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        if (itemColorCapability.setColor(meta, color)) {
            item.setItemMeta(meta);
            property.setValue(item);
            container.commit(Message.component("easyarmorstands.history.changed-color", Util.formatColor(color)));
            for (Runnable subscription : subscriptions) {
                subscription.run();
            }
        }
    }

    public void subscribe(@NotNull Runnable callback) {
        subscriptions.add(callback);
    }
}
