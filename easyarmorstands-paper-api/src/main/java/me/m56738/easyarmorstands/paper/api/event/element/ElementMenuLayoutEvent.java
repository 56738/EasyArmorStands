package me.m56738.easyarmorstands.paper.api.event.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.layout.MenuLayout;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ElementMenuLayoutEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Element element;
    private final MenuLayout menuLayout;

    public ElementMenuLayoutEvent(Player player, Element element, MenuLayout menuLayout) {
        super(player);
        this.element = element;
        this.menuLayout = menuLayout;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Element getElement() {
        return element;
    }

    public MenuLayout getMenuLayout() {
        return menuLayout;
    }
}
