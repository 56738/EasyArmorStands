package me.m56738.easyarmorstands.paper.api.event.player;

import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerCreateElementEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final ElementType type;
    private final PropertyContainer properties;
    private boolean cancelled;

    public PlayerCreateElementEvent(@NotNull Player who, @NotNull ElementType type, @NotNull PropertyContainer properties) {
        super(who);
        this.type = type;
        this.properties = PropertyContainer.immutable(properties);
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull ElementType getType() {
        return type;
    }

    public @NotNull PropertyContainer getProperties() {
        return properties;
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
