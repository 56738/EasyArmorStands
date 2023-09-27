package me.m56738.easyarmorstands.api.element;

import org.jetbrains.annotations.NotNull;

public interface EntityElementProviderRegistry {
    void register(@NotNull EntityElementProvider provider);
}
