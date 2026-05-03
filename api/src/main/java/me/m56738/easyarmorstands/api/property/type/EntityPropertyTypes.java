package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.BooleanFormatter;
import me.m56738.easyarmorstands.api.formatter.ItemStackFormatter;
import me.m56738.easyarmorstands.api.formatter.LocationFormatter;
import me.m56738.easyarmorstands.api.formatter.NumberFormatter;
import me.m56738.easyarmorstands.api.formatter.OptionalFormatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("PatternValidation")
@NullMarked
public final class EntityPropertyTypes {
    public static final PropertyType<Boolean> AI = PropertyType.builder(key("entity/ai"), Boolean.class)
            .name(translatable("easyarmorstands.property.ai.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.ai")
            .build();
    public static final PropertyType<Optional<Component>> CUSTOM_NAME = PropertyType.<Optional<Component>>builder(key("entity/custom_name"))
            .name(translatable("easyarmorstands.property.custom-name.name"))
            .formatter(new OptionalFormatter<>(value -> value))
            .permission("easyarmorstands.property.name")
            .build();
    public static final PropertyType<Boolean> CUSTOM_NAME_VISIBLE = PropertyType.builder(key("entity/custom_name/visible"), Boolean.class)
            .name(Component.translatable("easyarmorstands.property.custom-name-visible.name"))
            .formatter(BooleanFormatter.visibility())
            .permission("easyarmorstands.property.name.visible")
            .build();
    public static final KeyedPropertyType<EquipmentSlot, ItemStack> EQUIPMENT = new EnumKeyedPropertyType<>(EquipmentSlot.class,
            slot -> {
                String name = slot.name().toLowerCase(Locale.ROOT);
                return PropertyType.builder(key("entity/equipment/" + name), ItemStack.class)
                        .name(Component.translatable("easyarmorstands.property.equipment." + name.replace("_", "-") + ".name"))
                        .formatter(new ItemStackFormatter())
                        .permission("easyarmorstands.property.equipment." + name.replace("_", ""))
                        .build();
            });
    public static final PropertyType<Boolean> GLOWING = PropertyType.builder(key("entity/glowing"), Boolean.class)
            .name(translatable("easyarmorstands.property.glow.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.glow")
            .build();
    public static final PropertyType<Location> LOCATION = PropertyType.builder(key("entity/location"), Location.class)
            .name(translatable("easyarmorstands.property.location.name"))
            .formatter(new LocationFormatter())
            .permission("easyarmorstands.property.location")
            .build();
    public static final PropertyType<Double> SCALE = PropertyType.builder(key("entity/scale"), Double.class)
            .name(translatable("easyarmorstands.property.scale.name"))
            .formatter(NumberFormatter.pattern("0.0"))
            .permission("easyarmorstands.property.scale")
            .build();
    public static final PropertyType<Boolean> SILENT = PropertyType.builder(key("entity/silent"), Boolean.class)
            .name(translatable("easyarmorstands.property.silent.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.silent")
            .build();
    public static final PropertyType<Set<String>> TAGS = PropertyType.<Set<String>>builder(key("entity/tags"))
            .name(translatable("easyarmorstands.property.tags.name"))
            .formatter(value -> {
                if (value.size() > 8) {
                    return Component.translatable("easyarmorstands.property.tags.multiple", Component.text(value.size())).decorate(TextDecoration.ITALIC);
                } else if (value.isEmpty()) {
                    return Component.translatable("easyarmorstands.property.tags.none").decorate(TextDecoration.ITALIC);
                } else {
                    return Component.text(String.join(", ", value));
                }
            })
            .permission("easyarmorstands.property.tags")
            .build();
    public static final PropertyType<Boolean> VISIBLE = PropertyType.builder(key("entity/visible"), Boolean.class)
            .name(translatable("easyarmorstands.property.visibility.name"))
            .formatter(BooleanFormatter.visibility())
            .permission("easyarmorstands.property.visible")
            .build();

    private EntityPropertyTypes() {
    }
}
