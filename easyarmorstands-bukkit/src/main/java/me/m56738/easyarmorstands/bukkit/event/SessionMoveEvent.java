package me.m56738.easyarmorstands.bukkit.event;

import me.m56738.easyarmorstands.bukkit.platform.BukkitArmorStand;
import me.m56738.easyarmorstands.bukkit.platform.BukkitPlayer;
import me.m56738.easyarmorstands.core.session.Session;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class SessionMoveEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private final Session session;
    private final ArmorStand armorStand;
    private final Vector3dc position;
    private boolean cancelled;

    public SessionMoveEvent(@NotNull Session session, Vector3dc position) {
        super(((BukkitPlayer) session.getPlayer()).get());
        this.session = session;
        this.armorStand = ((BukkitArmorStand) session.getEntity()).get();
        this.position = new Vector3d(position);
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Session getSession() {
        return session;
    }

    public @NotNull Vector3dc getPosition() {
        return position;
    }

    public @NotNull ArmorStand getArmorStand() {
        return armorStand;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
