package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.property.ItemEntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EntityEquipmentProperty extends ItemEntityProperty<LivingEntity> {
    private final EquipmentCapability equipmentCapability;
    private final EquipmentSlot slot;
    private final String name;
    private final Component displayName;
    private final int index;

    public EntityEquipmentProperty(EquipmentCapability equipmentCapability, EquipmentSlot slot, String name, Component displayName, int row, int column) {
        this.equipmentCapability = equipmentCapability;
        this.slot = slot;
        this.name = name;
        this.displayName = displayName;
        this.index = 9 * row + column;
    }

    @Override
    public ItemStack getValue(LivingEntity entity) {
        ItemStack item = equipmentCapability.getItem(entity.getEquipment(), slot);
        if (item == null) {
            item = new ItemStack(Material.AIR);
        }
        return item;
    }

    @Override
    public void setValue(LivingEntity entity, ItemStack value) {
        equipmentCapability.setItem(entity.getEquipment(), slot, value);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull Class<LivingEntity> getEntityType() {
        return LivingEntity.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return displayName;
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.equipment";
    }

    @Override
    public int getSlotIndex() {
        return index;
    }
}
