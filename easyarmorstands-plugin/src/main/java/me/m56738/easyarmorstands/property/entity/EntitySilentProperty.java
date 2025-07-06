package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntitySilentProperty implements Property<Boolean> {
    private final Entity entity;

    public EntitySilentProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return EntityPropertyTypes.SILENT;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isSilent();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setSilent(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
