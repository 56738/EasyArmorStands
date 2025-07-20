package me.m56738.easyarmorstands.paper.api.event.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class SessionEvent extends PlayerEvent {
    private final Session session;

    public SessionEvent(@NotNull Session session) {
        super(PaperPlayer.toNative(session.player()));
        this.session = session;
    }

    public @NotNull Session getSession() {
        return session;
    }
}
