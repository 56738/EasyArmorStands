package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.editor.Session;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerSessionStopEvent extends PlayerEvent {
    private final Session session;

    public PlayerSessionStopEvent(Player player, Session session) {
        super(player);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
