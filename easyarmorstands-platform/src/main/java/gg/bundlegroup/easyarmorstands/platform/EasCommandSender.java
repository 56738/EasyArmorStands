package gg.bundlegroup.easyarmorstands.platform;

import net.kyori.adventure.audience.Audience;

public interface EasCommandSender extends EasWrapper, Audience {
    boolean hasPermission(String permission);
}
