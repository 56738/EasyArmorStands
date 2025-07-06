package me.m56738.easyarmorstands.api.property.type;

import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;

import static me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes.get;

public final class ItemDisplayPropertyTypes {
    public static final PropertyType<ItemStack> ITEM = get("item_display/item");
    public static final PropertyType<ItemDisplayTransform> TRANSFORM = get("item_display/transform");

    private ItemDisplayPropertyTypes() {
    }
}
