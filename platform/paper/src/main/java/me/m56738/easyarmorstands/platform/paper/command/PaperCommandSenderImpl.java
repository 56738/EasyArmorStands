package me.m56738.easyarmorstands.platform.paper.command;

import org.bukkit.command.CommandSender;

record PaperCommandSenderImpl(CommandSender sender) implements PaperCommandSender {
    @Override
    public CommandSender getNative() {
        return sender;
    }
}
