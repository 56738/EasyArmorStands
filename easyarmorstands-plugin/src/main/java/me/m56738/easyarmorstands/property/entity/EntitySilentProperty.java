package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.silent.SilentCapability;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntitySilentProperty implements Property<Boolean> {
    private final Entity entity;
    private final SilentCapability silentCapability;

    public EntitySilentProperty(Entity entity, SilentCapability silentCapability) {
        this.entity = entity;
        this.silentCapability = silentCapability;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return EntityPropertyTypes.SILENT;
    }

    @Override
    public @NotNull Boolean getValue() {
        return silentCapability.isSilent(entity);
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        silentCapability.setSilent(entity, value);
        return true;
    }
}
