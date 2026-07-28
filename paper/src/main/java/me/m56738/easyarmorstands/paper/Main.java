package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.paper.api.EasyArmorStandsPaper;
import me.m56738.easyarmorstands.paper.api.EasyArmorStandsPaperProvider;
import me.m56738.easyarmorstands.paper.command.ComponentSuggestionMapper;
import me.m56738.easyarmorstands.paper.listener.PaperToolListener;
import me.m56738.easyarmorstands.paper.listener.PaperVisibilityListener;
import me.m56738.easyarmorstands.paper.permission.PaperPermissionRegistrar;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.paper.PaperPlatform;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jspecify.annotations.Nullable;

public class Main extends JavaPlugin implements EasyArmorStandsPaperProvider {
    private final EasyArmorStandsHolder holder;
    private final MainThreadExecutor executor;
    private final TranslationManager translationManager;
    private final PaperCommandManager.Bootstrapped<EasCommandSender> commandManager;
    private final PaperPlatform platform;
    private final PaperPermissionRegistrar permissionRegistrar = new PaperPermissionRegistrar(getServer());
    private @Nullable EasyArmorStandsPaperImpl eas;

    public Main(EasyArmorStandsHolder holder, MainThreadExecutor executor, TranslationManager translationManager, PaperCommandManager.Bootstrapped<EasCommandSender> commandManager) {
        this.holder = holder;
        this.executor = executor;
        this.translationManager = translationManager;
        this.commandManager = commandManager;
        this.platform = new PaperPlatform(getServer());
        executor.setPlugin(this);
    }

    @Override
    public void onLoad() {
        platform.initialize(this);

        eas = new EasyArmorStandsPaperImpl(this, translationManager, platform, commandManager);
        holder.initialize(eas);

        eas.onLoad();

        Permissions.registerAll(platform, holder.get().propertyTypeRegistry(), permissionRegistrar);
    }

    @Override
    public void onEnable() {
        if (eas == null) {
            return;
        }

        platform.enable();

        commandManager.onEnable();
        commandManager.appendSuggestionMapper(new ComponentSuggestionMapper());

        new Metrics(this, 17911);

        eas.onEnable();

        getServer().getPluginManager().registerEvents(new PaperToolListener(eas), this);
        getServer().getPluginManager().registerEvents(new PaperVisibilityListener(eas), this);
        getServer().getScheduler().runTaskTimer(this, eas::update, 0, 1);
    }

    @Override
    public void onDisable() {
        if (eas != null) {
            eas.onDisable();
        }

        permissionRegistrar.unregisterAll();
    }

    @Override
    public EasyArmorStandsPaper getEasyArmorStands() {
        if (eas == null) {
            throw new IllegalStateException("EasyArmorStands is not loaded");
        }
        return eas;
    }
}
