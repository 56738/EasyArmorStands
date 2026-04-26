package me.m56738.easyarmorstands.api.editor;

import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.layer.LayerProvider;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.Collection;

@ApiStatus.NonExtendable
public interface Session {
    void pushLayer(@NotNull Layer layer);

    void pushLayer(@NotNull Layer layer, @Nullable Vector3dc cursor);

    void popLayer();

    /**
     * Pops all layers above the specified layer.
     * Does nothing if the specified layer is not present in this session.
     *
     * @param target The layer which should be the active layer after this method.
     */
    void returnToLayer(@NotNull Layer target);

    void clearLayers();

    @Contract(pure = true)
    @Nullable Layer getLayer();

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
    <T extends Layer> @Nullable T findLayer(@NotNull Class<T> type);

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
    @NotNull LayerProvider layerProvider();

    @Contract(pure = true)
    @NotNull Snapper snapper();
}
