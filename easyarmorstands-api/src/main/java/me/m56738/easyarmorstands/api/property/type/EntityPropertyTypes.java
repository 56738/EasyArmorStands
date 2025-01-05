package me.m56738.easyarmorstands.api.property.type;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("PatternValidation")
public class EntityPropertyTypes {
    public static final @NotNull PropertyType<Boolean> AI = get("entity/ai", Boolean.class);
    public static final @NotNull PropertyType<Optional<Component>> CUSTOM_NAME = get("entity/custom_name", new TypeToken<Optional<Component>>() {
    });
    public static final @NotNull PropertyType<Boolean> CUSTOM_NAME_VISIBLE = get("entity/custom_name/visible", Boolean.class);
    public static final @NotNull KeyedPropertyType<EquipmentSlot, ItemStack> EQUIPMENT = new EnumKeyedPropertyType<>(EquipmentSlot.class,
            slot -> get("entity/equipment/" + slot.name().toLowerCase(Locale.ROOT), ItemStack.class));
    public static final @NotNull PropertyType<Boolean> GLOWING = get("entity/glowing", Boolean.class);
    public static final @NotNull PropertyType<Location> LOCATION = get("entity/location", Location.class);
    public static final @NotNull PropertyType<Double> SCALE = get("entity/scale", Double.class);
    public static final @NotNull PropertyType<Boolean> SILENT = get("entity/silent", Boolean.class);
    public static final @NotNull PropertyType<Set<String>> TAGS = get("entity/tags", new TypeToken<Set<String>>() {
    });
    public static final @NotNull PropertyType<Boolean> VISIBLE = get("entity/visible", Boolean.class);

    private EntityPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.get(type));
    }
}
