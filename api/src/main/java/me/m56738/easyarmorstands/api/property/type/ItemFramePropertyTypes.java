package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.BooleanFormatter;
import me.m56738.easyarmorstands.api.formatter.ItemStackFormatter;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class ItemFramePropertyTypes {
    public static final PropertyType<ItemStack> ITEM = PropertyType.builder(key("item_frame/item"), ItemStack.class)
            .name(translatable("easyarmorstands.property.item.name"))
            .formatter(new ItemStackFormatter())
            .permission("easyarmorstands.property.itemframe.item")
            .build();
    public static final PropertyType<Boolean> FIXED = PropertyType.builder(key("item_frame/fixed"), Boolean.class)
            .name(translatable("easyarmorstands.property.item-frame.fixed.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.itemframe.fixed")
            .build();

    private ItemFramePropertyTypes() {
    }
}
