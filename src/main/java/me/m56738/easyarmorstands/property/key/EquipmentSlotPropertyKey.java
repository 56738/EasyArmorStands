package me.m56738.easyarmorstands.property.key;

import net.kyori.adventure.key.Key;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Locale;
import java.util.Objects;

public class EquipmentSlotPropertyKey<T> implements PropertyKey<T> {
    private final Key key;
    private final EquipmentSlot slot;

    public EquipmentSlotPropertyKey(Key key, EquipmentSlot slot) {
        this.key = key;
        this.slot = slot;
    }

    @Override
    public String toString() {
        return key.asString() + "." + slot.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentSlotPropertyKey<?> that = (EquipmentSlotPropertyKey<?>) o;
        return Objects.equals(key, that.key) && slot == that.slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, slot);
    }
}
