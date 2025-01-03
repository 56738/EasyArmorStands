package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A provider which can turn an entity into an element.
 *
 * @see EntityElementProviderRegistry
 */
public interface EntityElementProvider {
    /**
     * Attempts to create an element instance which represents the specified entity.
     * <p>
     * Returns {@code null} if this provider is not responsible for the entity.
     *
     * @param entity the entity
     * @return the element instance or {@code null}
     */
    @Nullable Element getElement(@NotNull Entity entity);

    /**
     * Returns the priority of this provider.
     * <p>
     * Providers with a higher priority are called first, until a provider returns a non-{@code null} element.
     *
     * @return the priority
     */
    default @NotNull Priority getPriority() {
        return Priority.NORMAL;
    }

    /**
     * The priority of an element provider.
     */
    enum Priority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST
    }
}
