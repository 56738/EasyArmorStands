package me.m56738.easyarmorstands.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;
import net.kyori.adventure.audience.Audience;

public interface CommandSender extends Audience, PlatformHolder {
    boolean hasPermission(String permission);
}
