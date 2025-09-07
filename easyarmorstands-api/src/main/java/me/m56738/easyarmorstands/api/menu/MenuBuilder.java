package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MenuBuilder {
    void setTitle(Component title);

    void addSlot(MenuSlot slot, MenuSlotOptions options);

    Menu build();
}
