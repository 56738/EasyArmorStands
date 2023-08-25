package me.m56738.easyarmorstands.display.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.block.data.BlockData;

public class BlockDisplayPropertyTypes {
    public static final PropertyType<BlockData> BLOCK = get("block_display/block", BlockData.class);

    private BlockDisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }
}
