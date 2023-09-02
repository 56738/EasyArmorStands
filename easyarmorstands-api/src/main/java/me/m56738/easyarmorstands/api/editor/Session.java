package me.m56738.easyarmorstands.api.editor;

import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.node.Node;
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

@ApiStatus.NonExtendable
public interface Session {
    void pushNode(@NotNull Node node);

    void pushNode(@NotNull Node node, @Nullable Vector3dc cursor);

    void popNode();

    void clearNode();

    @Contract(pure = true)
    @Nullable Node getNode();

    @Contract(pure = true)
    @Nullable Element getElement();

    @Contract(pure = true)
    <T extends Node> @Nullable T findNode(@NotNull Class<T> type);

    void addParticle(@NotNull Particle particle);

    void removeParticle(@NotNull Particle particle);

    @Contract(pure = true)
    double snapPosition(double value);

    @Contract(pure = true)
    double snapDegrees(double value);

    @Contract(pure = true)
    double snapAngle(double value);

    @Contract(pure = true)
    @NotNull Player player();

    @Contract(pure = true)
    @NotNull PropertyContainer properties(@NotNull Element element);

    @Contract(pure = true)
    @NotNull ParticleProvider particleProvider();

    @Contract(pure = true)
    @NotNull MenuButtonProvider menuEntryProvider();
}
