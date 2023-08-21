package me.m56738.easyarmorstands.api.element;

import org.jetbrains.annotations.ApiStatus;

public interface EntityElementProviderRegistry {
    static EntityElementProviderRegistry entityElementProviderRegistry() {
        return Holder.instance;
    }

    void register(EntityElementProvider provider);

    @ApiStatus.Internal
    class Holder {
        public static EntityElementProviderRegistry instance;
    }
}
