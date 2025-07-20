package me.m56738.easyarmorstands.api.editor;

import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Vector3dc;

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
    @UnmodifiableView
    Collection<Node> getAllNodes();

    @Contract(pure = true)
    double getScale(Vector3dc position);

    void addParticle(@NotNull Particle particle);

    void removeParticle(@NotNull Particle particle);

    @Contract(pure = true)
    @NotNull Player player();

    @Contract(pure = true)
    @NotNull EyeRay eyeRay();

    @Contract(pure = true)
    @NotNull ParticleProvider particleProvider();

    @Contract(pure = true)
    @NotNull MenuButtonProvider menuEntryProvider();

    @Contract(pure = true)
    @NotNull NodeProvider nodeProvider();

    @Contract(pure = true)
    @NotNull Snapper snapper();
}
