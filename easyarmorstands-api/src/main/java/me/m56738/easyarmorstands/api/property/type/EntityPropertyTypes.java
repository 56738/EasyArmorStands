package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
@SuppressWarnings("PatternValidation")
public final class EntityPropertyTypes {
    public static final PropertyType<Boolean> AI = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/ai"))
            .name(translatable("easyarmorstands.property.ai.name"))
            .permission("easyarmorstands.property.ai"));
    public static final PropertyType<Optional<Component>> CUSTOM_NAME = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/custom_name"))
            .name(translatable("easyarmorstands.property.custom-name.name"))
            .permission("easyarmorstands.property.name"));
    public static final PropertyType<Boolean> CUSTOM_NAME_VISIBLE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/custom_name/visible"))
            .name(translatable("easyarmorstands.property.custom-name-visible.name"))
            .permission("easyarmorstands.property.name.visible"));
    public static final KeyedPropertyType<EquipmentSlot, Item> EQUIPMENT = new EnumKeyedPropertyType<>(EquipmentSlot.class,
            slot -> PropertyType.build(b -> b
                    .key(key(EasyArmorStands.NAMESPACE, "entity/equipment/" + slot.name().toLowerCase(Locale.ROOT)))
                    .name(translatable("easyarmorstands.property.equipment." + slot.name().toLowerCase(Locale.ROOT).replace("_", "-") + ".name"))
                    .permission("easyarmorstands.property.equipment." + slot.name().toLowerCase(Locale.ROOT).replace("_", ""))));
    public static final PropertyType<Boolean> GLOWING = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/glowing"))
            .name(translatable("easyarmorstands.property.glow.name"))
            .permission("easyarmorstands.property.glow"));
    public static final PropertyType<Location> LOCATION = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/location"))
            .name(translatable("easyarmorstands.property.location.name"))
            .permission("easyarmorstands.property.location"));
    public static final PropertyType<Double> SCALE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/scale"))
            .name(translatable("easyarmorstands.property.scale.name"))
            .permission("easyarmorstands.property.scale"));
    public static final PropertyType<Boolean> SILENT = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/silent"))
            .name(translatable("easyarmorstands.property.silent.name"))
            .permission("easyarmorstands.property.silent"));
    public static final PropertyType<Set<String>> TAGS = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/tags"))
            .name(translatable("easyarmorstands.property.tags.name"))
            .permission("easyarmorstands.property.tags"));
    public static final PropertyType<Boolean> VISIBLE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "entity/visible"))
            .name(translatable("easyarmorstands.property.visibility.name"))
            .permission("easyarmorstands.property.visibility"));

    private EntityPropertyTypes() {
    }
}
