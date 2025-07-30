package me.m56738.easyarmorstands.paper.element;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.common.element.EntityElementReference;

public abstract class AbstractEntityElement implements EntityElement {
    private final Platform platform;
    private final EntityElementType type;
    private final Entity entity;
    private final PropertyRegistry properties = new PropertyRegistry();

    protected AbstractEntityElement(Platform platform, EntityElementType type, Entity entity) {
        this.platform = platform;
        this.type = type;
        this.entity = entity;
    }

    @Override
    public EntityElementType getType() {
        return type;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public PropertyRegistry getProperties() {
        return properties;
    }

    @Override
    public EntityElementReference getReference() {
        return new EntityElementReference(platform, type, entity);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
