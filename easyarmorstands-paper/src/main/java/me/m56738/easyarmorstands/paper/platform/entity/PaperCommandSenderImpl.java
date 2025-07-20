package me.m56738.easyarmorstands.paper.platform.entity;

import me.m56738.easyarmorstands.paper.api.platform.entity.PaperCommandSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;

public class PaperCommandSenderImpl implements PaperCommandSender, ForwardingAudience.Single {
    private final CommandSender sender;

    public PaperCommandSenderImpl(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public CommandSender getNative() {
        return sender;
    }

    @Override
    public Audience audience() {
        return sender;
    }
}
