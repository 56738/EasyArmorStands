package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.CommandSender;
import org.bukkit.entity.Player;

public interface PaperCommandSender extends CommandSender {
    static PaperCommandSender fromNative(org.bukkit.command.CommandSender nativeSender) {
        if (nativeSender instanceof Player nativePlayer) {
            return PaperPlayer.fromNative(nativePlayer);
        }
        return new PaperCommandSenderImpl(nativeSender);
    }

    static org.bukkit.command.CommandSender toNative(CommandSender sender) {
        return ((PaperCommandSender) sender).getNative();
    }

    org.bukkit.command.CommandSender getNative();

    @Override
    default boolean hasPermission(String permission) {
        return getNative().hasPermission(permission);
    }
}
