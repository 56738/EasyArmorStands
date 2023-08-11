package me.m56738.easyarmorstands.property.key;

import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.EquipmentSlot;

public interface PropertyKey<T> {
    static <T> PropertyKey<T> of(Key key) {
        return new SimplePropertyKey<>(key);
    }

    static <T> PropertyKey<T> of(Key key, ArmorStandPart part) {
        return new ArmorStandPartPropertyKey<>(key, part);
    }

    static <T> PropertyKey<T> of(Key key, EquipmentSlot slot) {
        return new EquipmentSlotPropertyKey<>(key, slot);
    }
}
