package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A request to spawn an element.
 *
 * @see me.m56738.easyarmorstands.api.EasyArmorStands#elementSpawnRequest(ElementType) Creating a request
 */
public interface ElementSpawnRequest {
    /**
     * Returns the type of the element which should be spawned.
     *
     * @return the type
     */
    @Contract(pure = true)
    @NotNull
    ElementType getType();

    /**
     * Returns the player who is performing the request.
     *
     * @return the player or {@code null}
     */
    @Contract(pure = true)
    @Nullable
    Player getPlayer();

    /**
     * Set which player is performing the request.
     * <p>
     * For example, this is used to perform permission checks and determine the default location.
     *
     * @param player the player or {@code null}
     */
    void setPlayer(@Nullable Player player);

    /**
     * The properties which will be applied to the element.
     * <p>
     * Can be modified.
     *
     * @return the modifiable property map
     */
    @Contract(pure = true)
    @NotNull PropertyMap getProperties();

    /**
     * Attempts to spawn the element.
     *
     * @return the spawned element or {@code null}
     */
    @Nullable Element spawn();
}
