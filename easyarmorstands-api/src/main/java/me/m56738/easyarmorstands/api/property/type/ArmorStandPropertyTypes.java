package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes.get;

@SuppressWarnings("PatternValidation")
public final class ArmorStandPropertyTypes {
    public static final @NotNull PropertyType<Boolean> ARMS = get("armor_stand/arms");
    public static final @NotNull PropertyType<Boolean> BASE_PLATE = get("armor_stand/base_plate");
    public static final @NotNull PropertyType<Boolean> CAN_TICK = get("armor_stand/can_tick");
    public static final @NotNull PropertyType<Boolean> GRAVITY = get("armor_stand/gravity");
    public static final @NotNull PropertyType<Boolean> INVULNERABLE = get("armor_stand/invulnerable");
    public static final @NotNull PropertyType<Boolean> LOCK = get("armor_stand/lock");
    public static final @NotNull PropertyType<Boolean> MARKER = get("armor_stand/marker");
    public static final @NotNull KeyedPropertyType<ArmorStandPart, EulerAngles> POSE = new EnumKeyedPropertyType<>(ArmorStandPart.class,
            part -> get("armor_stand/pose/" + part.name().toLowerCase(Locale.ROOT)));
    public static final @NotNull PropertyType<ArmorStandSize> SIZE = get("armor_stand/size");

    private ArmorStandPropertyTypes() {
    }
}
