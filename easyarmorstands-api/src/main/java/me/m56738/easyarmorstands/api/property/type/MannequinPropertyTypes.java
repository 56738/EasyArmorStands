package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.Hand;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.platform.profile.Profile;
import net.kyori.adventure.text.Component;

import java.util.Locale;
import java.util.Optional;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("PatternValidation")
public final class MannequinPropertyTypes {
    public static final PropertyType<Hand> MAIN_HAND = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "mannequin/main_hand"))
            .name(translatable("easyarmorstands.property.mannequin.main-hand.name"))
            .permission("easyarmorstands.property.mannequin.mainhand"));
    public static final PropertyType<Profile> PROFILE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "mannequin/profile"))
            .name(translatable("easyarmorstands.property.mannequin.profile.name"))
            .permission("easyarmorstands.property.mannequin.profile"));
    public static final PropertyType<Boolean> IMMOVABLE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "mannequin/immovable"))
            .name(translatable("easyarmorstands.property.mannequin.immovable.name"))
            .permission("easyarmorstands.property.mannequin.immovable"));
    public static final PropertyType<Optional<Component>> DESCRIPTION = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "mannequin/description"))
            .name(translatable("easyarmorstands.property.mannequin.description.name"))
            .permission("easyarmorstands.property.mannequin.description"));
    public static final KeyedPropertyType<SkinPart, Boolean> SKIN_PART_VISIBLE = new EnumKeyedPropertyType<>(SkinPart.class,
            part -> PropertyType.build(b -> b
                    .key(key(EasyArmorStands.NAMESPACE, "mannequin/part/" + part.name().toLowerCase(Locale.ROOT) + "/visible"))
                    .name(translatable("easyarmorstands.property.mannequin.part.visible.name", translatable("easyarmorstands.mannequin.part." + part.name().toLowerCase(Locale.ROOT))))
                    .permission("easyarmorstands.property.mannequin.part.visible." + part.name().toLowerCase(Locale.ROOT))));

    private MannequinPropertyTypes() {
    }
}
