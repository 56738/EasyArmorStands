package me.m56738.easyarmorstands.property.key;

import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class EquipmentSlotKey<T> implements Key<T> {
    private final Key<T> key;
    private final EquipmentSlot slot;

    public EquipmentSlotKey(Key<T> key, EquipmentSlot slot) {
        this.key = key;
        this.slot = slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentSlotKey<?> that = (EquipmentSlotKey<?>) o;
        return Objects.equals(key, that.key) && slot == that.slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, slot);
    }
}
