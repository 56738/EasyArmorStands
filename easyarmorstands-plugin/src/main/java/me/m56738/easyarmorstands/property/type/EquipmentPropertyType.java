package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class EquipmentPropertyType extends ItemPropertyType {
    private final EquipmentSlot slot;

    public EquipmentPropertyType(@NotNull Key key, EquipmentSlot slot) {
        super(key);
        this.slot = slot;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }
}
