package gg.bundlegroup.easyarmorstands.bukkit;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import gg.bundlegroup.easyarmorstands.bukkit.addon.Addon;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ArmSwingListener;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ArmorStandCanTickAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityHider;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityNameAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityPersistenceSetter;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntitySpawner;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EquipmentAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.FeatureProvider;
import gg.bundlegroup.easyarmorstands.bukkit.feature.FeatureProvider.Priority;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ItemProvider;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ParticleSpawner;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ToolChecker;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlatform;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitWrapper;
import gg.bundlegroup.easyarmorstands.common.Main;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.common.session.SessionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private BukkitPlatform platform;
    private Main main;

    public static EasyArmorStands getInstance() {
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
                loadFeature(EquipmentAccessor.Provider.class),
                loadFeature(EntityNameAccessor.Provider.class),
                loadFeature(ArmorStandCanTickAccessor.Provider.class),
                Objects.requireNonNull(loadFeature(ItemProvider.Provider.class), "Item provider not found"));

        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            try {
                commandManager.registerBrigadier();
            } catch (BukkitCommandManager.BrigadierFailureException e) {
                getLogger().log(Level.WARNING, "Failed to register Brigadier mappings", e);
            }
        }

        getServer().getPluginManager().registerEvents(platform, this);

        ArmSwingListener armSwingListener = loadFeature(ArmSwingListener.Provider.class);
        if (armSwingListener != null) {
            getServer().getPluginManager().registerEvents(armSwingListener, this);
        }

        main = new Main(platform);

        for (Addon addon : ServiceLoader.load(Addon.class, getClassLoader())) {
            if (addon.isSupported()) {
                getLogger().info("Enabling " + addon.getName() + " support");
                try {
                    addon.enable(this);
                } catch (Throwable t) {
                    getLogger().log(Level.SEVERE, "Failed to enable " + addon.getName() + " support", t);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        if (main != null) {
            main.close();
        }
    }

    public BukkitPlatform getPlatform() {
        return platform;
    }

    public SessionManager getSessionManager() {
        return main.getSessionManager();
    }

    public CommandManager<EasCommandSender> getCommandManager() {
        return main.getCommandManager();
    }

    public AnnotationParser<EasCommandSender> getAnnotationParser() {
        return main.getAnnotationParser();
    }

    private <T extends FeatureProvider<F>, F> F loadFeature(Class<T> type) {
        for (Priority priority : Priority.values()) {
            F feature = loadFeature(type, priority);
            if (feature != null) {
                getLogger().info("Using " + feature);
                return feature;
            }
        }
        getLogger().warning(type + " not found");
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
