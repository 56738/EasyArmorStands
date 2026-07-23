package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.element.ReferenceProvider;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.entity.Entity;

public class ReferenceProviderImpl implements ReferenceProvider {
    private final Platform platform;

    public ReferenceProviderImpl(Platform platform) {
        this.platform = platform;
    }

    @Override
    public <E extends Entity> EntityElementReference<E> createEntityElementReference(EntityElementType<E> type, E entity) {
        return new EntityElementReferenceImpl<>(platform, type, entity);
    }
}
