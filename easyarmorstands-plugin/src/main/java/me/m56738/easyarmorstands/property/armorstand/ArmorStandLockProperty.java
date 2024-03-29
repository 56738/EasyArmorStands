package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandLockProperty implements Property<Boolean> {
    private final ArmorStand entity;
    private final LockCapability lockCapability;

    public ArmorStandLockProperty(ArmorStand entity, LockCapability lockCapability) {
        this.entity = entity;
        this.lockCapability = lockCapability;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.LOCK;
    }

    @Override
    public @NotNull Boolean getValue() {
        return lockCapability.isLocked(entity);
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        lockCapability.setLocked(entity, value);
        return true;
    }
}
