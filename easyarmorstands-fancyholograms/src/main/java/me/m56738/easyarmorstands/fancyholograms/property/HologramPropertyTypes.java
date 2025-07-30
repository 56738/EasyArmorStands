package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.data.HologramData;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class HologramPropertyTypes {
    public static final PropertyType<HologramData> DATA = get("fancyholograms/data");
    public static final PropertyType<List<String>> TEXT = get("fancyholograms/text");

    private HologramPropertyTypes() {
    }

    static <T> PropertyType<T> get(@KeyPattern.Value String name) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name));
    }
}
