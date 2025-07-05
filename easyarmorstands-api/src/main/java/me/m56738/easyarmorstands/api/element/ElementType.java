package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The type of the element.
 */
public interface ElementType {
    /**
     * Attempt to create an element with the specified properties.
     *
     * @param properties the properties which should be applied to the element
     * @return the created element, or {@code null} on failure
     */
    @Nullable Element createElement(@NotNull PropertyContainer properties);

    /**
     * Modify the default properties used when {@link ElementSpawnRequest spawning} an element.
     *
     * @param properties the properties which may be modified
     */
    default void applyDefaultProperties(@NotNull PropertyMap properties) {
    }

    /**
     * Whether the element is spawned at the eye height of the player instead of at their feet.
     *
     * @return true if spawned at eye height
     */
    default boolean isSpawnedAtEyeHeight() {
        return true;
    }

    /**
     * Returns the display name of this element type.
     * <p>
     * For example, this is displayed in spawn buttons and history actions.
     *
     * @return the display name
     */
    @Contract(pure = true)
    @NotNull Component getDisplayName();

    /**
     * Checks whether a player is allowed to spawn an element of this type.
     *
     * @param player the player
     * @return whether the element may be spawned
     */
    @Contract(pure = true)
    boolean canSpawn(@NotNull Player player);
}
