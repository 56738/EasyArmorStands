package me.m56738.easyarmorstands.paper.api.platform.entity;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;

record PaperCommandSenderImpl(
        CommandSender nativeSender
) implements PaperCommandSender, ForwardingAudience.Single {
    @Override
    public CommandSender getNative() {
        return nativeSender;
    }

    @Override
    public Audience audience() {
        return nativeSender;
    }
}
