package me.m56738.easyarmorstands.paper.api.platform.entity;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.entity.Player;

record PaperPlayerImpl(
        Player nativePlayer
) implements PaperPlayer, ForwardingAudience.Single {
    @Override
    public Player getNative() {
        return nativePlayer;
    }

    @Override
    public Audience audience() {
        return nativePlayer;
    }
}
