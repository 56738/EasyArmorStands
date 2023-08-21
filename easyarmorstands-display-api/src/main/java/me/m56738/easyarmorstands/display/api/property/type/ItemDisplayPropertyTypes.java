package me.m56738.easyarmorstands.display.api.property.type;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayPropertyTypes {
    public static final PropertyType<ItemStack> ITEM = get("item_display/item", ItemStack.class);
    public static final PropertyType<ItemDisplayTransform> TRANSFORM = get("item_display/transform", ItemDisplayTransform.class);

    private ItemDisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return PropertyTypeRegistry.Holder.instance.get(Key.key("easyarmorstands", name), type);
    }
}
