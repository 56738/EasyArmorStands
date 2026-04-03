package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EntityCustomNameProperty implements Property<Optional<Component>> {
    private final Entity entity;
    private final ComponentCapability componentCapability;

    public EntityCustomNameProperty(Entity entity, ComponentCapability componentCapability) {
        this.entity = entity;
        this.componentCapability = componentCapability;
    }

    @Override
    public @NotNull PropertyType<Optional<Component>> getType() {
        return EntityPropertyTypes.CUSTOM_NAME;
    }

    @Override
    public @NotNull Optional<Component> getValue() {
        return Optional.ofNullable(componentCapability.getCustomName(entity));
    }

    @Override
    public boolean setValue(@NotNull Optional<Component> value) {
        componentCapability.setCustomName(entity, value.orElse(null));
        return true;
    }
}
