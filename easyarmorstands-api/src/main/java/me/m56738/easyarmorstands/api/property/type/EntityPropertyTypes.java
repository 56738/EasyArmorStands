package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.world.Location;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("PatternValidation")
public final class EntityPropertyTypes {
    public static final @NotNull PropertyType<Boolean> AI = get("entity/ai");
    public static final @NotNull PropertyType<Optional<Component>> CUSTOM_NAME = EntityPropertyTypes.get("entity/custom_name");
    public static final @NotNull PropertyType<Boolean> CUSTOM_NAME_VISIBLE = get("entity/custom_name/visible");
    public static final @NotNull KeyedPropertyType<EquipmentSlot, ItemStack> EQUIPMENT = new EnumKeyedPropertyType<>(EquipmentSlot.class,
            slot -> get("entity/equipment/" + slot.name().toLowerCase(Locale.ROOT)));
    public static final @NotNull PropertyType<Boolean> GLOWING = get("entity/glowing");
    public static final @NotNull PropertyType<Location> LOCATION = get("entity/location");
    public static final @NotNull PropertyType<Double> SCALE = get("entity/scale");
    public static final @NotNull PropertyType<Boolean> SILENT = get("entity/silent");
    public static final @NotNull PropertyType<Set<String>> TAGS = EntityPropertyTypes.get("entity/tags");
    public static final @NotNull PropertyType<Boolean> VISIBLE = get("entity/visible");

    private EntityPropertyTypes() {
    }

    static <T> PropertyType<T> get(@KeyPattern.Value String name) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name));
    }
}
