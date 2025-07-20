package me.m56738.easyarmorstands.paper.platform;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import org.bukkit.plugin.Plugin;

public class PaperPlatformImpl implements PaperPlatform {
    private final Plugin plugin;

    public PaperPlatformImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getEasyArmorStandsVersion() {
        return plugin.getPluginMeta().getVersion();
    }
}
