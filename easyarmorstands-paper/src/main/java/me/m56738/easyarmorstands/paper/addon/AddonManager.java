package me.m56738.easyarmorstands.paper.addon;

import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddonManager {
    private final List<Addon> addons = new ArrayList<>();
    private final EasyArmorStandsPlugin plugin;
    private final Logger logger;

    public AddonManager(EasyArmorStandsPlugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    public void load(ClassLoader loader) {
        List<AddonFactory<?>> addonFactories = new ArrayList<>();
        for (AddonFactory<?> addonFactory : ServiceLoader.load(AddonFactory.class, loader)) {
            addonFactories.add(addonFactory);
        }

        for (AddonFactory<?> addonFactory : addonFactories) {
            load(addonFactory);
        }
    }

    public <T extends Addon> void load(AddonFactory<T> addonFactory) {
        try {
            if (!addonFactory.isEnabled()) {
                return;
            }
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Failed to check if addon is enabled: " + addonFactory.getClass().getName(), e);
            return;
        }

        try {
            if (!addonFactory.isAvailable()) {
                return;
            }
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Failed to check if addon is available: " + addonFactory.getClass().getName(), e);
            return;
        }

        Addon addon;
        try {
            addon = addonFactory.create(plugin);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Failed to initialize addon: " + addonFactory.getClass().getName(), e);
            return;
        }

        addons.add(addon);
    }

    public void enable() {
        for (Addon addon : addons) {
            String name = addon.name();
            if (name != null) {
                logger.info("Enabling " + name + " integration");
            }
            try {
                addon.enable();
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Failed to enable addon: " + addon.getClass().getName(), e);
                return;
            }
        }
    }

    public void disable() {
        for (int i = addons.size() - 1; i >= 0; i--) {
            Addon addon = addons.get(i);
            try {
                addon.disable();
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Failed to disable addon: " + addon.getClass().getName(), e);
                return;
            }
        }
        addons.clear();
    }

    public void reload() {
        for (Addon addon : addons) {
            try {
                addon.reload();
            } catch (Throwable e) {
                logger.log(Level.SEVERE, "Failed to reload addon: " + addon.getClass().getName(), e);
                return;
            }
        }
    }

    public Collection<Addon> getAddons() {
        return Collections.unmodifiableCollection(addons);
    }
}
