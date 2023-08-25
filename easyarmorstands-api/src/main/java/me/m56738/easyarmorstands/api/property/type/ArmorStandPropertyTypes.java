package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.util.EulerAngle;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("PatternValidation")
public class ArmorStandPropertyTypes {
    public static final PropertyType<Boolean> ARMS = get("armor_stand/arms", Boolean.class);
    public static final PropertyType<Boolean> BASE_PLATE = get("armor_stand/base_plate", Boolean.class);
    public static final PropertyType<Boolean> CAN_TICK = get("armor_stand/can_tick", Boolean.class);
    public static final PropertyType<Boolean> GRAVITY = get("armor_stand/gravity", Boolean.class);
    public static final PropertyType<Boolean> INVULNERABLE = get("armor_stand/invulnerable", Boolean.class);
    public static final PropertyType<Boolean> LOCK = get("armor_stand/lock", Boolean.class);
    public static final PropertyType<Boolean> MARKER = get("armor_stand/marker", Boolean.class);
    public static final Map<ArmorStandPart, PropertyType<EulerAngle>> POSE = new EnumMap<>(ArmorStandPart.class);
    public static final PropertyType<ArmorStandSize> SIZE = get("armor_stand/size", ArmorStandSize.class);

    static {
        for (ArmorStandPart part : ArmorStandPart.values()) {
            POSE.put(part, get("armor_stand/pose/" + part.name().toLowerCase(Locale.ROOT), EulerAngle.class));
        }
    }

    private ArmorStandPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }
}
