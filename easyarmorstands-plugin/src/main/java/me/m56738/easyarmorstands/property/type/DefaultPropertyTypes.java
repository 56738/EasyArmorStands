package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.inventory.EquipmentSlot;

public class DefaultPropertyTypes {
    public DefaultPropertyTypes(PropertyTypeRegistry registry) {
        registerEntityProperties(registry);
        registerArmorStandProperties(registry);
        registerDisplayProperties(registry);
        registerMannequinProperties(registry);
    }

    private void registerEntityProperties(PropertyTypeRegistry registry) {
        registry.register(EntityPropertyTypes.AI);
        registry.register(EntityPropertyTypes.CUSTOM_NAME);
        registry.register(EntityPropertyTypes.CUSTOM_NAME_VISIBLE);
        registry.register(EntityPropertyTypes.GLOWING);
        registry.register(EntityPropertyTypes.LOCATION);
        registry.register(EntityPropertyTypes.SCALE);
        registry.register(EntityPropertyTypes.SILENT);
        registry.register(EntityPropertyTypes.TAGS);
        registry.register(EntityPropertyTypes.VISIBLE);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            registry.register(EntityPropertyTypes.EQUIPMENT.get(slot));
        }
    }

    private void registerArmorStandProperties(PropertyTypeRegistry registry) {
        registry.register(ArmorStandPropertyTypes.ARMS);
        registry.register(ArmorStandPropertyTypes.BASE_PLATE);
        registry.register(ArmorStandPropertyTypes.CAN_TICK);
        registry.register(ArmorStandPropertyTypes.GRAVITY);
        registry.register(ArmorStandPropertyTypes.INVULNERABLE);
        registry.register(ArmorStandPropertyTypes.LOCK);
        registry.register(ArmorStandPropertyTypes.MARKER);
        registry.register(ArmorStandPropertyTypes.SIZE);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(ArmorStandPropertyTypes.POSE.get(part));
        }
    }

    private void registerDisplayProperties(PropertyTypeRegistry registry) {
        registry.register(DisplayPropertyTypes.BILLBOARD);
        registry.register(DisplayPropertyTypes.BOX_HEIGHT);
        registry.register(DisplayPropertyTypes.BOX_WIDTH);
        registry.register(DisplayPropertyTypes.BRIGHTNESS);
        registry.register(DisplayPropertyTypes.GLOW_COLOR);
        registry.register(DisplayPropertyTypes.LEFT_ROTATION);
        registry.register(DisplayPropertyTypes.RIGHT_ROTATION);
        registry.register(DisplayPropertyTypes.SCALE);
        registry.register(DisplayPropertyTypes.TRANSLATION);
        registry.register(DisplayPropertyTypes.VIEW_RANGE);
        registry.register(ItemDisplayPropertyTypes.ITEM);
        registry.register(ItemDisplayPropertyTypes.TRANSFORM);
        registry.register(TextDisplayPropertyTypes.ALIGNMENT);
        registry.register(TextDisplayPropertyTypes.BACKGROUND);
        registry.register(TextDisplayPropertyTypes.LINE_WIDTH);
        registry.register(TextDisplayPropertyTypes.SEE_THROUGH);
        registry.register(TextDisplayPropertyTypes.SHADOW);
        registry.register(TextDisplayPropertyTypes.TEXT);
        registry.register(BlockDisplayPropertyTypes.BLOCK);
        registry.register(InteractionPropertyTypes.RESPONSIVE);
    }

    private void registerMannequinProperties(PropertyTypeRegistry registry) {
        registry.register(MannequinPropertyTypes.MAIN_HAND);
        registry.register(MannequinPropertyTypes.PROFILE);
        registry.register(MannequinPropertyTypes.IMMOVABLE);
        registry.register(MannequinPropertyTypes.POSE);
        registry.register(MannequinPropertyTypes.DESCRIPTION);
        for (SkinPart part : SkinPart.values()) {
            registry.register(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(part));
        }
    }
}
