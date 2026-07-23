package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.platform.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface ReferenceProvider {
    <E extends Entity> EntityElementReference<E> createEntityElementReference(EntityElementType<E> type, E entity);
}
