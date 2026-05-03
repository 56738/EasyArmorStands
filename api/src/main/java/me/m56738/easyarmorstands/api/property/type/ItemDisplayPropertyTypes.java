package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.ItemStackFormatter;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class ItemDisplayPropertyTypes {
    public static final PropertyType<ItemStack> ITEM = PropertyType.builder(key("item_display/item"), ItemStack.class)
            .name(translatable("easyarmorstands.property.item-display.item.name"))
            .formatter(new ItemStackFormatter())
            .permission("easyarmorstands.property.display.item")
            .build();
    public static final PropertyType<ItemDisplayTransform> TRANSFORM = PropertyType.builder(key("item_display/transform"), ItemDisplayTransform.class)
            .name(translatable("easyarmorstands.property.item-display.transform.name"))
            .formatter(value -> translatable("easyarmorstands.property.item-display.transform." + value.name().toLowerCase(Locale.ROOT).replace("_", "-")))
            .permission("easyarmorstands.property.display.item.transform")
            .build();

    private ItemDisplayPropertyTypes() {
    }
}
