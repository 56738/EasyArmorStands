package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.entity.display.ItemDisplayTransform;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import org.jspecify.annotations.NullMarked;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class ItemDisplayPropertyTypes {
    public static final PropertyType<Item> ITEM = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "item_display/item"))
            .name(translatable("easyarmorstands.property.item-display.item.name"))
            .permission("easyarmorstands.property.display.item"));
    public static final PropertyType<ItemDisplayTransform> TRANSFORM = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "item_display/transform"))
            .name(translatable("easyarmorstands.property.item-display.transform.name"))
            .permission("easyarmorstands.property.display.item.transform"));

    private ItemDisplayPropertyTypes() {
    }
}
