package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.paper.api.EasyArmorStandsPaper;
import me.m56738.easyarmorstands.paper.api.EasyArmorStandsPaperProvider;
import me.m56738.easyarmorstands.paper.command.ComponentSuggestionMapper;
import me.m56738.easyarmorstands.paper.listener.ClipboardListener;
import me.m56738.easyarmorstands.paper.listener.MenuListener;
import me.m56738.easyarmorstands.paper.listener.PlayerListener;
import me.m56738.easyarmorstands.paper.listener.SessionListener;
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

        Permissions.registerAll(platform, permissionRegistrar);

        eas = new EasyArmorStandsPaperImpl(this, translationManager, platform, commandManager);
        holder.initialize(eas);

        eas.onLoad();
    }

    @Override
    public void onEnable() {
        if (eas == null) {
            return;
        }

        commandManager.onEnable();
        commandManager.appendSuggestionMapper(new ComponentSuggestionMapper());

        new Metrics(this, 17911);

        eas.onEnable();

        SessionListener sessionListener = new SessionListener(eas);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(new MenuListener(eas), this);
        getServer().getPluginManager().registerEvents(new ClipboardListener(eas), this);
        getServer().getScheduler().runTaskTimer(this, eas::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, sessionListener::update, 0, 1);

        getServer().getPluginManager().registerEvents(new PlayerListener(eas), this);
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
