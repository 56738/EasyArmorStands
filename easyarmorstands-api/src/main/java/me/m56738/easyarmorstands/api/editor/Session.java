package me.m56738.easyarmorstands.api.editor;

import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@ApiStatus.NonExtendable
public interface Session {
    void pushNode(@NotNull Node node);

    void pushNode(@NotNull Node node, @Nullable Vector3dc cursor);

    void popNode();

    /**
     * Pops all nodes above the specified node.
     * Does nothing if the specified node is not present in this session.
     *
     * @param target The node which should be the active node after this method.
     */
    void returnToNode(@NotNull Node target);

    void clearNodes();

    @Contract(pure = true)
    @Nullable Node getNode();

    /**
     * Returns the currently selected element, if exactly one element is selected.
     *
     * @return the selected element
     */
    @Contract(pure = true)
    @Nullable Element getElement();

    /**
     * Returns all currently selected elements. Empty if no element is selected.
     *
     * @return all selected elements
     */
    @Contract(pure = true)
    @NotNull Collection<Element> getElements();

    @Contract(pure = true)
    <T extends Node> @Nullable T findNode(@NotNull Class<T> type);

    @Contract(pure = true)
    double getScale(Vector3dc position);

    void addParticle(@NotNull Particle particle);

    void removeParticle(@NotNull Particle particle);

    @Contract(pure = true)
    @NotNull Player player();

    @Contract(pure = true)
    @NotNull EyeRay eyeRay();

    /**
     * Returns the properties of an element, but with added permission checks and history tracking.
     * <p>
     * Changes will be performed immediately (if allowed).
     * History actions are created when any property container tracked by the player is
     * {@link PropertyContainer#commit() committed}.
     *
     * @param element the element whose properties should be returned
     * @return a property container with permission checks and history tracking
     */
    @Contract(pure = true)
    @NotNull PropertyContainer properties(@NotNull Element element);

    @Contract(pure = true)
    @NotNull ParticleProvider particleProvider();

    @Contract(pure = true)
    @NotNull MenuButtonProvider menuEntryProvider();

    @Contract(pure = true)
    @NotNull NodeProvider nodeProvider();

    @Contract(pure = true)
    @NotNull Snapper snapper();
}
