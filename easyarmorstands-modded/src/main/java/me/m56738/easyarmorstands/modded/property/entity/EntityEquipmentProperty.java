package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import net.minecraft.world.entity.LivingEntity;

public class EntityEquipmentProperty extends EntityProperty<LivingEntity, Item> {
    private final EquipmentSlot slot;
    private final ModdedPlatform platform;
    private final net.minecraft.world.entity.EquipmentSlot nativeSlot;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot, ModdedPlatform platform) {
        super(entity);
        this.slot = slot;
        this.platform = platform;
        this.nativeSlot = switch (slot) {
            case HAND -> net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            case OFF_HAND -> net.minecraft.world.entity.EquipmentSlot.OFFHAND;
            case FEET -> net.minecraft.world.entity.EquipmentSlot.FEET;
            case LEGS -> net.minecraft.world.entity.EquipmentSlot.LEGS;
            case CHEST -> net.minecraft.world.entity.EquipmentSlot.CHEST;
            case HEAD -> net.minecraft.world.entity.EquipmentSlot.HEAD;
            case BODY -> net.minecraft.world.entity.EquipmentSlot.BODY;
            case SADDLE -> net.minecraft.world.entity.EquipmentSlot.SADDLE;
        };
    }

    @Override
    public PropertyType<Item> getType() {
        return EntityPropertyTypes.EQUIPMENT.get(slot);
    }

    @Override
    public Item getValue() {
        return platform.getItem(entity.getItemBySlot(nativeSlot));
    }

    @Override
    public boolean setValue(Item value) {
        entity.setItemSlot(nativeSlot, ModdedItem.toNative(value));
        return true;
    }
}
