package me.m56738.easyarmorstands.api.editor;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleFactory;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4dc;
import org.joml.Vector2dc;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface Session {
    void pushNode(@NotNull Node node);

    void pushNode(@NotNull Node node, @Nullable Vector3dc cursor);

    void replaceNode(@NotNull Node node);

    void popNode();

    void clearNode();

    @Nullable Node getNode();

    @Nullable Element getElement();

    <T extends Node> @Nullable T findNode(Class<T> type);

    void setActionBar(ComponentLike actionBar);

    void setTitle(ComponentLike title);

    void setSubtitle(ComponentLike subtitle);

    void addParticle(Particle particle);

    void removeParticle(Particle particle);

    @Contract(pure = true)
    double snapPosition(double value);

    @Contract(pure = true)
    double snapAngle(double value);

    @Contract(pure = true)
    @NotNull Player player();

    @Contract(pure = true)
    @NotNull PropertyContainer properties(@NotNull Element element);

    @Contract(pure = true)
    @NotNull EyeRay eyeRay();

    @Contract(pure = true)
    @NotNull EyeRay eyeRay(Vector2dc cursor);

    @Contract(pure = true)
    @NotNull Matrix4dc eyeMatrix();

    @Contract(pure = true)
    @NotNull ParticleFactory particleFactory();
}
