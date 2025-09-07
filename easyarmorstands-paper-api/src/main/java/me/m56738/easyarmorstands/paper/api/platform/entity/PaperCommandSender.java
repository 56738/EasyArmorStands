package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.CommandSender;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatformHolder;

public interface PaperCommandSender extends CommandSender, PaperPlatformHolder {
    static org.bukkit.command.CommandSender toNative(CommandSender sender) {
        return ((PaperCommandSender) sender).getNative();
    }

    org.bukkit.command.CommandSender getNative();

    @Override
    default boolean hasPermission(String permission) {
        return getNative().hasPermission(permission);
    }
}
