package me.m56738.easyarmorstands.display.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.KeyPattern;
import org.bukkit.block.data.BlockData;

public class BlockDisplayPropertyTypes {
    public static final PropertyType<BlockData> BLOCK = get("block_display/block", BlockData.class);

    private BlockDisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.get(type));
    }
}
