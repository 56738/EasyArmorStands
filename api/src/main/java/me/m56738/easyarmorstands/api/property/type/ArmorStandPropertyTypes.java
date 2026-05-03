package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.formatter.BooleanFormatter;
import me.m56738.easyarmorstands.api.formatter.EulerAngleFormatter;
import org.bukkit.util.EulerAngle;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("PatternValidation")
@NullMarked
public final class ArmorStandPropertyTypes {
    public static final PropertyType<Boolean> ARMS = PropertyType.builder(key("armor_stand/arms"), Boolean.class)
            .name(translatable("easyarmorstands.property.armor-stand.arms.name"))
            .formatter(BooleanFormatter.visibility())
            .permission("easyarmorstands.property.armorstand.arms")
            .build();
    public static final PropertyType<Boolean> BASE_PLATE = PropertyType.builder(key("armor_stand/base_plate"), Boolean.class)
            .name(translatable("easyarmorstands.property.armor-stand.base-plate.name"))
            .formatter(BooleanFormatter.visibility())
            .permission("easyarmorstands.property.armorstand.baseplate")
            .build();
    public static final PropertyType<Boolean> CAN_TICK = PropertyType.builder(key("armor_stand/can_tick"), Boolean.class)
            .name(translatable("easyarmorstands.property.armor-stand.can-tick.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.armorstand.cantick")
            .build();
    public static final PropertyType<Boolean> GRAVITY = PropertyType.builder(key("armor_stand/gravity"), Boolean.class)
            .name(translatable("easyarmorstands.property.gravity.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.gravity")
            .build();
    public static final PropertyType<Boolean> INVULNERABLE = PropertyType.builder(key("armor_stand/invulnerable"), Boolean.class)
            .name(translatable("easyarmorstands.property.invulnerability.name"))
            .formatter(BooleanFormatter.translatable(
                    "easyarmorstands.property.invulnerability.enabled",
                    "easyarmorstands.property.invulnerability.disabled"))
            .permission("easyarmorstands.property.invulnerable")
            .build();
    public static final PropertyType<Boolean> LOCK = PropertyType.builder(key("armor_stand/lock"), Boolean.class)
            .name(translatable("easyarmorstands.property.armor-stand.lock.name"))
            .formatter(BooleanFormatter.translatable(
                    "easyarmorstands.property.armor-stand.lock.enabled",
                    "easyarmorstands.property.armor-stand.lock.disabled"))
            .permission("easyarmorstands.property.armorstand.lock")
            .build();
    public static final PropertyType<Boolean> MARKER = PropertyType.builder(key("armor_stand/marker"), Boolean.class)
            .name(translatable("easyarmorstands.property.armor-stand.marker.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.armorstand.marker")
            .build();
    public static final KeyedPropertyType<ArmorStandPart, EulerAngle> POSE = new EnumKeyedPropertyType<>(ArmorStandPart.class,
            part -> PropertyType.builder(key("armor_stand/pose/" + part.name().toLowerCase(Locale.ROOT)), EulerAngle.class)
                    .name(translatable("easyarmorstands.property.armor-stand.pose.name", part.displayName()))
                    .formatter(new EulerAngleFormatter())
                    .permission("easyarmorstands.property.armorstand.pose." + part.name().toLowerCase(Locale.ROOT).replace("_", ""))
                    .build());
    public static final PropertyType<ArmorStandSize> SIZE = PropertyType.builder(key("armor_stand/size"), ArmorStandSize.class)
            .name(translatable("easyarmorstands.property.armor-stand.size.name"))
            .formatter(ArmorStandSize::displayName)
            .permission("easyarmorstands.property.armorstand.size")
            .build();

    private ArmorStandPropertyTypes() {
    }
}
