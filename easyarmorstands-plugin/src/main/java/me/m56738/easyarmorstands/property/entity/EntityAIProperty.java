package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityAIProperty implements Property<Boolean> {
    private final LivingEntity entity;

    public EntityAIProperty(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return EntityPropertyTypes.AI;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.hasAI();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setAI(value);
        return true;
    }
}
