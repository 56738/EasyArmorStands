package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.Hand;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.KeyPattern;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;

import java.util.Locale;
import java.util.Optional;

@SuppressWarnings("PatternValidation")
public final class MannequinPropertyTypes {
    public static final PropertyType<Hand> MAIN_HAND = get("mannequin/main_hand", Hand.class);
    public static final PropertyType<Profile> PROFILE = get("mannequin/profile", Profile.class);
    public static final PropertyType<Boolean> IMMOVABLE = get("mannequin/immovable", Boolean.class);
    public static final PropertyType<Optional<Component>> DESCRIPTION = get("mannequin/description", new TypeToken<Optional<Component>>() {
    });
    public static final KeyedPropertyType<SkinPart, Boolean> SKIN_PART_VISIBLE = new EnumKeyedPropertyType<>(SkinPart.class,
            part -> get("mannequin/part/" + part.name().toLowerCase(Locale.ROOT) + "/visible", Boolean.class));

    private MannequinPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.get(type));
    }
}
