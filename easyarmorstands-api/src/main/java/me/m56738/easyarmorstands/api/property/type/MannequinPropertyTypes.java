package me.m56738.easyarmorstands.api.property.type;

import com.google.common.reflect.TypeToken;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.SkinPart;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.MainHand;

import java.util.Locale;
import java.util.Optional;

@SuppressWarnings("PatternValidation")
public final class MannequinPropertyTypes {
    public static final PropertyType<MainHand> MAIN_HAND = get("mannequin/main_hand", MainHand.class);
    @SuppressWarnings("UnstableApiUsage")
    public static final PropertyType<ResolvableProfile> PROFILE = get("mannequin/profile", ResolvableProfile.class);
    public static final PropertyType<Boolean> IMMOVABLE = get("mannequin/immovable", Boolean.class);
    public static final PropertyType<Optional<Component>> DESCRIPTION = get("mannequin/description", new TypeToken<>() {
    });
    public static final KeyedPropertyType<SkinPart, Boolean> SKIN_PART_VISIBLE = new EnumKeyedPropertyType<>(SkinPart.class,
            part -> get("mannequin/part/" + part.name().toLowerCase(Locale.ROOT) + "/visible", Boolean.class));

    private MannequinPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.of(type));
    }
}
