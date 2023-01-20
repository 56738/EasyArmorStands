package gg.bundlegroup.easyarmorstands.platform.bukkit;

import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import gg.bundlegroup.easyarmorstands.Main;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityHider;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityPersistenceSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntitySpawner;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EquipmentAccessor;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.FeatureProvider;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.FeatureProvider.Priority;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ParticleSpawner;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ToolChecker;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ServiceLoader;
import java.util.logging.Level;

public class BukkitMain extends JavaPlugin {
    private static BukkitMain instance;
    private BukkitPlatform platform;
    private Main main;

    public static BukkitMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        PaperCommandManager<EasCommandSender> commandManager;
        try {
            commandManager = new PaperCommandManager<>(
                    this,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    this::wrapCommandSender,
                    this::unwrapCommandSender);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        platform = new BukkitPlatform(
                this,
                commandManager,
                loadFeature(EntityGlowSetter.Provider.class),
                loadFeature(EntityHider.Provider.class),
                loadFeature(EntityPersistenceSetter.Provider.class),
                loadFeature(EntitySpawner.Provider.class),
                loadFeature(ToolChecker.Provider.class),
                loadFeature(ParticleSpawner.Provider.class),
                loadFeature(EquipmentAccessor.Provider.class));

        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            try {
                commandManager.registerBrigadier();
            } catch (BukkitCommandManager.BrigadierFailureException e) {
                getLogger().log(Level.WARNING, "Failed to register Brigadier mappings", e);
            }
        }

        getServer().getPluginManager().registerEvents(platform, this);

        main = new Main(platform);
    }

    @Override
    public void onDisable() {
        main.close();
    }

    private <T extends FeatureProvider<F>, F> F loadFeature(Class<T> type) {
        for (Priority priority : Priority.values()) {
            F feature = loadFeature(type, priority);
            if (feature != null) {
                return feature;
            }
        }
        return null;
    }

    private <T extends FeatureProvider<F>, F> F loadFeature(Class<T> type, Priority priority) {
        for (T provider : ServiceLoader.load(type, getClassLoader())) {
            if (provider.getPriority() != priority) {
                continue;
            }

            try {
                if (!provider.isSupported()) {
                    continue;
                }
            } catch (Throwable e) {
                getLogger().log(Level.SEVERE, "Failed to check " + provider.getClass().getName(), e);
                continue;
            }

            F feature;
            try {
                feature = provider.create();
            } catch (Throwable e) {
                getLogger().log(Level.SEVERE, "Failed to create " + provider.getClass().getName(), e);
                continue;
            }

            return feature;
        }

        return null;
    }

    private EasCommandSender wrapCommandSender(CommandSender sender) {
        return platform.getCommandSender(sender);
    }

    @SuppressWarnings("unchecked")
    private CommandSender unwrapCommandSender(EasCommandSender sender) {
        return ((BukkitWrapper<? extends CommandSender>) sender).get();
    }
}
