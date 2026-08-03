package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerSpawnMenuOpenEvent extends PlayerEvent {
    private final MenuBuilder menuBuilder;

    public PlayerSpawnMenuOpenEvent(Player player, MenuBuilder menuBuilder) {
        super(player);
        this.menuBuilder = menuBuilder;
    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }
}
