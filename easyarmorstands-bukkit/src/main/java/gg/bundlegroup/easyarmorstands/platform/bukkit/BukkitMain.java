package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.Main;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ServiceLoader;
import java.util.logging.Level;

public class BukkitMain extends JavaPlugin {
    private Main main;

    @Override
    public void onEnable() {
        BukkitPlatform platform = new BukkitPlatform(
                this,
                loadFeature(EntityGlowSetter.Provider.class),
                loadFeature(EntityHider.Provider.class),
                loadFeature(EntityPersistenceSetter.Provider.class),
                loadFeature(EntitySpawner.Provider.class));
        main = new Main(platform);
    }

    @Override
    public void onDisable() {
        main.close();
    }

    private <T extends FeatureProvider<F>, F> F loadFeature(Class<T> type) {
        for (T provider : ServiceLoader.load(type, getClassLoader())) {
            try {
                if (!provider.isSupported()) {
                    continue;
                }
            } catch (Throwable e) {
                getLogger().log(Level.SEVERE, "Failed to check " + provider.getClass().getName());
                continue;
            }

            F feature;
            try {
                feature = provider.create();
            } catch (Throwable e) {
                getLogger().log(Level.SEVERE, "Failed to create " + provider.getClass().getName());
                continue;
            }

            return feature;
        }

        return null;
    }
}
