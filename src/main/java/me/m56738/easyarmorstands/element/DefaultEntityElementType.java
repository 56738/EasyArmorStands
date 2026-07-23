package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class DefaultEntityElementType<E extends Entity> extends SimpleEntityElementType<E> {
    public DefaultEntityElementType(EasyArmorStandsCommon eas, EntityType entityType, Class<E> entityClass) {
        super(eas, entityType, entityClass);
    }
}
