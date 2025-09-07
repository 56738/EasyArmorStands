package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import org.jetbrains.annotations.NotNull;

/**
 * Registry for {@link EntityElementProvider entity element providers}.
 *
 * @see EasyArmorStands#getEntityElementProviderRegistry()
 */
public interface EntityElementProviderRegistry {
    /**
     * Registers an element provider.
     *
     * @param provider the provider
     */
    void register(@NotNull EntityElementProvider provider);
}
