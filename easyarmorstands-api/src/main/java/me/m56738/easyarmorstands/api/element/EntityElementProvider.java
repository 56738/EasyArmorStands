package me.m56738.easyarmorstands.api.element;

import net.kyori.adventure.key.Keyed;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A provider which can turn an entity into an element.
 *
 * @see EntityElementProviderRegistry
 */
public interface EntityElementProvider extends Keyed {
    /**
     * Returns whether {@link #getElement} should be called for the passed entity when attempting to find the responsible element provider.
     *
     * @param entity the entity
     * @return whether this provider is responsible for resolving the passed entity into an element
     */
    boolean isApplicable(@NotNull Entity entity);

    /**
     * Attempts to create an element instance which represents the specified entity.
     * <p>
     * Returns {@code null} if this provider cannot create an element for the entity.
     * <p>
     * May be called even if {@link #isApplicable} returns false, for example, if {@link me.m56738.easyarmorstands.api.EasyArmorStands#setEntityElementProvider(Entity, EntityElementProvider) the entity references this provider}.
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
