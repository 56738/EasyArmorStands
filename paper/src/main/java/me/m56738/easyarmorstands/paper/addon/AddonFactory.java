package me.m56738.easyarmorstands.paper.addon;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import org.bukkit.plugin.Plugin;

public interface AddonFactory<T extends Addon> {
    boolean isEnabled(EasConfig config);

    boolean isAvailable();

    default Priority getPriority() {
        return Priority.NORMAL;
    }

    T create(Plugin plugin, EasyArmorStandsPaperImpl eas);
}
