package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.LivingEntity;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EntityEquipmentProperty implements Property<ItemStack> {
    private final LivingEntity entity;
    private final EquipmentSlot slot;
    private final PropertyType<ItemStack> type;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot) {
        this.entity = entity;
        this.slot = slot;
        this.type = EntityPropertyTypes.EQUIPMENT.get(slot);
    }

    @Override
    public @NotNull PropertyType<ItemStack> getType() {
        return type;
    }

    @Override
    public @NotNull ItemStack getValue() {
        return entity.getEquipment(slot);
    }

    @Override
    public boolean setValue(@NotNull ItemStack value) {
        entity.setEquipment(slot, value);
        return true;
    }
}
