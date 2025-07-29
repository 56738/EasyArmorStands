package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.platform.entity.display.ItemDisplayTransform;
import me.m56738.easyarmorstands.api.platform.inventory.Item;

import static me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes.get;

public final class ItemDisplayPropertyTypes {
    public static final PropertyType<Item> ITEM = get("item_display/item");
    public static final PropertyType<ItemDisplayTransform> TRANSFORM = get("item_display/transform");

    private ItemDisplayPropertyTypes() {
    }
}
