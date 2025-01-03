package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A source which can discover elements inside a bounding box.
 *
 * @see me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode#addSource(ElementDiscoverySource) Registering sources
 */
public interface ElementDiscoverySource {
    /**
     * Returns entries for all potential elements inside the provided bounding box.
     * <p>
     * Should be as fast as possible and not perform any expensive checks.
     *
     * @param world    the queried world
     * @param box      the queried bounding box
     * @param consumer a consumer which should be called for each potential element
     */
    void discover(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer);
}
