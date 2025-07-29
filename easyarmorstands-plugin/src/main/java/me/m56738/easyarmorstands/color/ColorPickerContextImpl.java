package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.util.Util;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperColorAdapter;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColorPickerContextImpl implements ColorPickerContext {
    private final Player player;
    private final Element element;
    private final PropertyType<Item> type;
    private final Property<Item> untrackedProperty;

    public ColorPickerContextImpl(Player player, Element element, PropertyType<Item> type) {
        this.player = player;
        this.element = element;
        this.type = type;
        this.untrackedProperty = element.getProperties().get(type);
    }

    public ColorPickerContextImpl(Player player, ItemPropertySlot slot) {
        this(player, slot.getElement(), slot.getType());
    }

    @Override
    public @NotNull Item item() {
        return untrackedProperty.getValue();
    }

    @Override
    public @NotNull Color getColor() {
        ItemStack item = PaperItem.toNative(untrackedProperty.getValue());
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            return PaperColorAdapter.fromNative(leatherArmorMeta.getColor());
        }
        if (meta instanceof MapMeta mapMeta) {
            if (mapMeta.hasColor()) {
                return PaperColorAdapter.fromNative(Objects.requireNonNull(mapMeta.getColor()));
            } else {
                return Color.ofRGB(0x46402E);
            }
        }
        return Color.WHITE;
    }

    @Override
    public void setColor(@NotNull Color color) {
        try (ManagedChangeContext context = EasyArmorStands.get().changeContext().create(player)) {
            Property<Item> property = context.getProperties(element).get(type);
            ItemStack item = PaperItem.toNative(property.getValue());
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
            if (setColor(meta, color)) {
                item.setItemMeta(meta);
                property.setValue(PaperItem.fromNative(item));
                context.commit(Message.component("easyarmorstands.history.changed-color", Util.formatColor(color)));
            }
        }
    }

    private boolean setColor(ItemMeta meta, Color color) {
        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            leatherArmorMeta.setColor(PaperColorAdapter.toNative(color));
            return true;
        }
        if (meta instanceof MapMeta mapMeta) {
            mapMeta.setColor(PaperColorAdapter.toNative(color));
            return true;
        }
        return false;
    }
}
