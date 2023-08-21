package me.m56738.easyarmorstands.addon;

import me.m56738.easyarmorstands.EasyArmorStands;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;

public class AddonLoader {
    private final EasyArmorStands plugin;
    private final ClassLoader classLoader;
    private final Map<Class<?>, Addon> addonMap = new HashMap<>();

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
                    addonMap.put(addon.getClass(), addon);
                }
            } catch (Throwable e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to process addon " + addon.getClass().getName(), e);
            }
        }
    }

    public void reload() {
        for (Addon addon : addonMap.values()) {
            addon.reload(plugin);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Addon> @Nullable T get(Class<T> type) {
        return (T) addonMap.get(type);
    }
}
