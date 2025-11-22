package me.m56738.easyarmorstands.api.event.player;

import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerCommitElementEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Element element;

    public PlayerCommitElementEvent(@NotNull Player who, @NotNull Element element) {
        super(who);
        this.element = element;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Element getElement() {
        return element;
    }
}
