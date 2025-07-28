package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import org.jspecify.annotations.Nullable;

public interface EntityElementType extends ElementType {
    EntityType getEntityType();

    // TODO nullable?
    @Nullable
    EntityElement getElement(Entity entity);
}
