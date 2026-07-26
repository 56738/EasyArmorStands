package me.m56738.easyarmorstands.platform.event;

import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.MenuDragCallback;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

public interface EventType<C> extends Keyed {
    static <C> EventType<C> of(Key key, EventDispatcher<C> dispatcher) {
        return new EventTypeImpl<>(key, dispatcher);
    }

    EventType<MenuClickCallback> MENU_CLICK = of(Platform.key("menu_click"), callbacks ->
            (player, inventory, slot, click) -> {
                for (MenuClickCallback callback : callbacks) {
                    if (callback.onClick(player, inventory, slot, click)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<MenuDragCallback> MENU_DRAG = of(Platform.key("menu_drag"), callbacks ->
            (player, inventory) -> {
                for (MenuDragCallback callback : callbacks) {
                    if (callback.onDrag(player, inventory)) {
                        return true;
                    }
                }
                return false;
            });

    EventDispatcher<C> dispatcher();
}
