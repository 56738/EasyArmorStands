package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityGlowingProperty implements Property<Boolean> {
    private final Entity entity;
    private final GlowCapability glowCapability;

    public EntityGlowingProperty(Entity entity, GlowCapability glowCapability) {
        this.entity = entity;
        this.glowCapability = glowCapability;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return EntityPropertyTypes.GLOWING;
    }

    @Override
    public Boolean getValue() {
        return glowCapability.isGlowing(entity);
    }

    @Override
    public boolean setValue(Boolean value) {
        glowCapability.setGlowing(entity, value);
        return true;
    }
}
