package me.m56738.easyarmorstands.paper.platform.entity;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperCommandSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;

public record PaperCommandSenderImpl(
        PaperPlatform platform, CommandSender nativeSender
) implements PaperCommandSender, ForwardingAudience.Single {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public CommandSender getNative() {
        return nativeSender;
    }

    @Override
    public Audience audience() {
        return nativeSender;
    }
}
