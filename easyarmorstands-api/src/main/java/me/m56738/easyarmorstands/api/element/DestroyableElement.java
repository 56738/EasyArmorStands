package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An element which can be destroyed.
 */
public interface DestroyableElement extends Element {
    /**
     * Destroys this element.
     */
    void destroy();

    /**
     * Checks whether a player is allowed to destroy this element.
     *
     * @param player the player
     * @return whether the element may be destroyed
     */
    @Contract(pure = true)
    boolean canDestroy(@NotNull Player player);
}
