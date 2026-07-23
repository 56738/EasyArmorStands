package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.color.ColorPicker;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.paper.addon.AddonManager;
import me.m56738.easyarmorstands.paper.api.EasyArmorStandsPaper;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeManager;
import me.m56738.easyarmorstands.paper.color.ColorPickerContextImpl;
import me.m56738.easyarmorstands.paper.element.PaperEntitySpawnEggProvider;
import me.m56738.easyarmorstands.paper.event.PaperEventDispatcher;
import me.m56738.easyarmorstands.paper.particle.PaperGizmoParticleProviderFactory;
import me.m56738.easyarmorstands.paper.region.RegionListenerManager;
import me.m56738.easyarmorstands.paper.session.PaperSessionToolProvider;
import me.m56738.easyarmorstands.paper.update.UpdateManager;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.PaperPlatform;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.Plugin;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

public class EasyArmorStandsPaperImpl extends EasyArmorStandsCommon implements EasyArmorStandsPaper {
    private final Plugin plugin;
    private final SessionToolProvider sessionToolProvider;
    private final RegionListenerManager regionPrivilegeManager;
    private final AddonManager addonManager;
    private final PaperEventDispatcher eventDispatcher;
    private @Nullable BukkitGizmos gizmos;
    private @Nullable ParticleProviderFactory particleProviderFactory;
    private @Nullable UpdateManager updateManager;

    public EasyArmorStandsPaperImpl(Plugin plugin, TranslationManager translationManager, PaperPlatform platform, PaperCommandManager<EasCommandSender> commandManager) {
        super(translationManager, platform, commandManager);
        this.plugin = plugin;
        this.sessionToolProvider = new PaperSessionToolProvider(this);
        this.regionPrivilegeManager = new RegionListenerManager();
        this.addonManager = new AddonManager(plugin, this, getLogger());
        this.eventDispatcher = new PaperEventDispatcher();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public Path getConfigFolder() {
        return plugin.getDataFolder().toPath();
    }

    @Override
    public ComponentLogger getLogger() {
        return plugin.getComponentLogger();
    }

    @Override
    public ClassLoader getClassLoader() {
        return plugin.getClass().getClassLoader();
    }

    @Override
    public PaperEventDispatcher eventDispatcher() {
        return eventDispatcher;
    }

    @Override
    public ParticleProviderFactory particleProviderFactory() {
        if (particleProviderFactory == null) {
            throw new IllegalStateException();
        }
        return particleProviderFactory;
    }

    @Override
    public SessionToolProvider sessionToolProvider() {
        return sessionToolProvider;
    }

    @Override
    public RegionPrivilegeManager regionPrivilegeManager() {
        return regionPrivilegeManager;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        addonManager.load(getClassLoader());
    }

    @Override
    public void onEnable() {
        gizmos = BukkitGizmos.create(plugin);
        particleProviderFactory = new PaperGizmoParticleProviderFactory(gizmos);
        super.onEnable();
        addonManager.enable();
        try {
            loadUpdateChecker();
        } catch (Exception e) {
            getLogger().warn("Failed to initialize update checks");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        addonManager.disable();
        regionPrivilegeManager.unregisterAll();
        if (gizmos != null) {
            gizmos.close();
            gizmos = null;
        }
        particleProviderFactory = null;
        if (updateManager != null) {
            updateManager.unregister();
        }
    }

    @Override
    public void reload() {
        super.reload();
        addonManager.reload();
    }

    @Override
    public Menu createColorPicker(Player player, Property<ItemStack> property) {
        return ColorPicker.create(this, player, new ColorPickerContextImpl(platform(), property));
    }

    @Override
    public boolean isColorPickerSupported(ItemStack item) {
        return ColorPickerContextImpl.hasColor(PaperItemStack.toNative(item).getItemMeta());
    }

    @Override
    public ItemStack createEntitySpawnEgg(Entity entity) {
        return PaperItemStack.fromNative(PaperEntitySpawnEggProvider.createSpawnEgg(PaperEntity.toNative(entity)));
    }

    private void loadUpdateChecker() {
        if (getConfiguration().updateCheck.enabled) {
            if (updateManager == null) {
                updateManager = new UpdateManager(plugin, Permissions.UPDATE_NOTIFY);
            }
        } else {
            if (updateManager != null) {
                updateManager.unregister();
                updateManager = null;
            }
        }
    }
}
