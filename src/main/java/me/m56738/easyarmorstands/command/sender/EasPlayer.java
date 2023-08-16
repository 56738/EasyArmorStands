package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.event.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.event.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3dc;

public class EasPlayer extends EasCommandSender implements ChangeContext {
    private final @NotNull Player player;
    private final @NotNull History history;

    public EasPlayer(@NotNull Player player, @NotNull Audience audience) {
        super(player, audience);
        this.player = player;
        this.history = EasyArmorStands.getInstance().getHistory(player);
    }

    public EasPlayer(@NotNull Player player) {
        this(player, EasyArmorStands.getInstance().getAdventure().player(player));
    }

    public Vector3dc position() {
        return Util.toVector3d(player.getLocation());
    }

    public Vector3dc eyePosition() {
        return Util.toVector3d(player.getEyeLocation());
    }

    public Vector3dc eyeDirection() {
        return Util.toVector3d(player.getEyeLocation().getDirection());
    }

    public Matrix4dc eyeMatrix() {
        Location eyeLocation = player.getEyeLocation();
        return new Matrix4d()
                .translation(Util.toVector3d(eyeLocation))
                .rotateY(-Math.toRadians(eyeLocation.getYaw()))
                .rotateX(Math.toRadians(eyeLocation.getPitch()));
    }

    public float yaw() {
        return player.getLocation().getYaw();
    }

    public float pitch() {
        return player.getLocation().getPitch();
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public boolean isFlying() {
        return player.isFlying();
    }

    public boolean isValid() {
        return player.isValid();
    }

    @Override
    public @NotNull Player get() {
        return player;
    }

    @Override
    public @NotNull History history() {
        return history;
    }

    public @Nullable Session session() {
        return EasyArmorStands.getInstance().getSessionManager().getSession(player);
    }

    @Override
    public boolean canCreateElement(ElementType type, PropertyContainer properties) {
        PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public boolean canDestroyElement(DestroyableElement element) {
        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
