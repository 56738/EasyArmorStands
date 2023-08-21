package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypes;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameProperty implements Property<@Nullable Component> {
    private final Entity entity;
    private final ComponentCapability componentCapability;

    public EntityCustomNameProperty(Entity entity, ComponentCapability componentCapability) {
        this.entity = entity;
        this.componentCapability = componentCapability;
    }

    @Override
    public @NotNull PropertyType<@Nullable Component> getType() {
        return PropertyTypes.ENTITY_CUSTOM_NAME;
    }

    @Override
    public @Nullable Component getValue() {
        return componentCapability.getCustomName(entity);
    }

    @Override
    public boolean setValue(@Nullable Component value) {
        componentCapability.setCustomName(entity, value);
        return true;
    }
}
