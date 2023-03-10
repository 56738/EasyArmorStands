package me.m56738.easyarmorstands.addon;

import me.m56738.easyarmorstands.EasyArmorStands;

import java.util.ServiceLoader;
import java.util.logging.Level;

public class AddonLoader {
    private final EasyArmorStands plugin;
    private final ClassLoader classLoader;

    public AddonLoader(EasyArmorStands plugin, ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.plugin = plugin;
    }

    public void load() {
        for (Addon addon : ServiceLoader.load(Addon.class, classLoader)) {
            try {
                if (addon.isSupported()) {
                    plugin.getLogger().info("Enabling " + addon.getName() + " integration");
                    addon.enable(plugin);
                }
            } catch (Throwable e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to process addon " + addon.getClass().getName(), e);
            }
        }
    }
}
