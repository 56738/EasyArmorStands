package me.m56738.easyarmorstands.display.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.KeyPattern;

public class InteractionPropertyTypes {
    public static final PropertyType<Boolean> RESPONSIVE = get("interaction/responsive", Boolean.class);

    private InteractionPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.get(type));
    }
}
