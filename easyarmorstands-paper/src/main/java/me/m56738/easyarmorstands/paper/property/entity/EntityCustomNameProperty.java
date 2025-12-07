package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

import java.util.Optional;

public class EntityCustomNameProperty extends EntityProperty<Entity, Optional<Component>> {
    public EntityCustomNameProperty(Entity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Optional<Component>> getType() {
        return EntityPropertyTypes.CUSTOM_NAME;
    }

    @Override
    public Optional<Component> getValue() {
        return Optional.ofNullable(entity.customName());
    }

    @Override
    public boolean setValue(Optional<Component> value) {
        entity.customName(value.orElse(null));
        return true;
    }
}
