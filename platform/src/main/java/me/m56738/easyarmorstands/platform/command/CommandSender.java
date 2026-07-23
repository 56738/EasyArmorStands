package me.m56738.easyarmorstands.platform.command;

import net.kyori.adventure.audience.Audience;

public interface CommandSender extends Audience {
    boolean hasPermission(String permission);

    boolean isPermissionSet(String permission);
}
