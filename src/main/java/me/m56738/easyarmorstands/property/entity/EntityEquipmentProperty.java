package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
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
        EntityEquipment equipment = entity.getEquipment();
        return Util.wrapItem(equipment != null ? equipment.getItem(slot) : null);
    }

    @Override
    public boolean setValue(@NotNull ItemStack value) {
        // Refuse to mutate equipment of an invalid entity (e.g. removed or in an unloaded chunk).
        // Without this guard the in-memory write would silently fail to persist, while the
        // menu still hands the previous value back to the player — duplicating items.
        if (!entity.isValid()) {
            return false;
        }
        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) {
            return false;
        }
        equipment.setItem(slot, value, true);
        return true;
    }
}
