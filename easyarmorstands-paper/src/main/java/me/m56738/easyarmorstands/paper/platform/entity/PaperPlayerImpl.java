package me.m56738.easyarmorstands.paper.platform.entity;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.entity.Player;

public record PaperPlayerImpl(
        PaperPlatform platform,
        Player nativePlayer
) implements PaperPlayer, ForwardingAudience.Single {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public Player getNative() {
        return nativePlayer;
    }

    @Override
    public Audience audience() {
        return nativePlayer;
    }
}
