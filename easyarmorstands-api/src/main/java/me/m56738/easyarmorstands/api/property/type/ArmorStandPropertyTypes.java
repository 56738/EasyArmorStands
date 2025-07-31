package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
@SuppressWarnings("PatternValidation")
public final class ArmorStandPropertyTypes {
    public static final @NotNull PropertyType<Boolean> ARMS = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/arms"))
            .name(translatable("easyarmorstands.property.armor-stand.arms.name"))
            .permission("easyarmorstands.property.armorstand.arms"));
    public static final @NotNull PropertyType<Boolean> BASE_PLATE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/base_plate"))
            .name(translatable("easyarmorstands.property.armor-stand.base-plate.name"))
            .permission("easyarmorstands.property.armorstand.baseplate"));
    public static final @NotNull PropertyType<Boolean> CAN_TICK = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/can_tick"))
            .name(translatable("easyarmorstands.property.armor-stand.can-tick.name"))
            .permission("easyarmorstands.property.armorstand.cantick"));
    public static final @NotNull PropertyType<Boolean> GRAVITY = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/gravity"))
            .name(translatable("easyarmorstands.property.gravity.name"))
            .permission("easyarmorstands.property.gravity"));
    public static final @NotNull PropertyType<Boolean> INVULNERABLE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/invulnerable"))
            .name(translatable("easyarmorstands.property.invulnerability.name"))
            .permission("easyarmorstands.property.invulnerable"));
    public static final @NotNull PropertyType<Boolean> LOCK = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/lock"))
            .name(translatable("easyarmorstands.property.armor-stand.lock.name"))
            .permission("easyarmorstands.property.armorstand.lock"));
    public static final @NotNull PropertyType<Boolean> MARKER = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/marker"))
            .name(translatable("easyarmorstands.property.armor-stand.marker.name"))
            .permission("easyarmorstands.property.armorstand.marker"));
    public static final @NotNull KeyedPropertyType<ArmorStandPart, EulerAngles> POSE = new EnumKeyedPropertyType<>(ArmorStandPart.class,
            part -> PropertyType.build(b -> b
                    .key(key(EasyArmorStands.NAMESPACE, "armor_stand/pose/" + part.name().toLowerCase(Locale.ROOT)))
                    .name(translatable("easyarmorstands.property.armor-stand.pose.name", translatable("easyarmorstands.armor-stand-part." + part.name().toLowerCase(Locale.ROOT).replace("_", "-"))))
                    .permission("easyarmorstands.property.armorstand.pose." + part.name().toLowerCase(Locale.ROOT).replace("_", ""))));
    public static final @NotNull PropertyType<ArmorStandSize> SIZE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "armor_stand/size"))
            .name(translatable("easyarmorstands.property.armor-stand.size.name"))
            .permission("easyarmorstands.property.armorstand.size"));

    private ArmorStandPropertyTypes() {
    }
}

