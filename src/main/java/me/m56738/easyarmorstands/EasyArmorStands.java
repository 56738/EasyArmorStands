package me.m56738.easyarmorstands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.paper.PaperCommandManager;
import me.m56738.easyarmorstands.addon.AddonLoader;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.command.CapabilityInjectionService;
import me.m56738.easyarmorstands.command.CommandSenderWrapper;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.command.EntityInjectionService;
import me.m56738.easyarmorstands.command.EntityPreprocessor;
import me.m56738.easyarmorstands.command.GlobalCommands;
import me.m56738.easyarmorstands.command.NoEntityException;
import me.m56738.easyarmorstands.command.NoSessionException;
import me.m56738.easyarmorstands.command.NodeValueArgumentParser;
import me.m56738.easyarmorstands.command.PipelineExceptionHandler;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.SessionInjector;
import me.m56738.easyarmorstands.command.SessionPreprocessor;
import me.m56738.easyarmorstands.command.ValueNodeInjector;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private SessionManager sessionManager;
    private HistoryManager historyManager;
    private BukkitAudiences adventure;
    private PaperCommandManager<EasCommandSender> commandManager;
    private AnnotationParser<EasCommandSender> annotationParser;

    public static EasyArmorStands getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        new Metrics(this, 17911);

        loader.load();

        sessionManager = new SessionManager();
        historyManager = new HistoryManager();
        adventure = BukkitAudiences.create(this);

        SessionListener sessionListener = new SessionListener(this, sessionManager);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(historyManager, this);
        getServer().getScheduler().runTaskTimer(this, sessionManager::update, 0, 1);

        CommandSenderWrapper senderWrapper = new CommandSenderWrapper(adventure);

        try {
            commandManager = new PaperCommandManager<>(
                    this,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    senderWrapper::wrap,
                    EasCommandSender::get);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            try {
                commandManager.registerBrigadier();
            } catch (BukkitCommandManager.BrigadierFailureException e) {
                getLogger().log(Level.WARNING, "Failed to register Brigadier mappings");
            }
        }

        new MinecraftExceptionHandler<EasCommandSender>()
                .withArgumentParsingHandler()
                .withInvalidSyntaxHandler()
                .withNoPermissionHandler()
                .withCommandExecutionHandler()
                .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SENDER,
                        (sender, e) -> Component.text("Only players can use this command", NamedTextColor.RED))
                .apply(commandManager, s -> s);

        commandManager.registerExceptionHandler(NoSessionException.class,
                (sender, e) -> sender.sendMessage(e.getComponent()));
        commandManager.registerExceptionHandler(NoEntityException.class,
                (sender, e) -> sender.sendMessage(e.getComponent()));

        commandManager.registerCommandPreProcessor(new EntityPreprocessor<>());
        commandManager.registerCommandPreProcessor(new SessionPreprocessor<>(sessionManager, EasCommandSender::get));

        PipelineExceptionHandler.register(commandManager);

        commandManager.parameterInjectorRegistry().registerInjector(
                Session.class, new SessionInjector<>());

        commandManager.parameterInjectorRegistry().registerInjector(
                ValueNode.class, new ValueNodeInjector<>());

        commandManager.parameterInjectorRegistry().registerInjectionService(new EntityInjectionService<>());

        commandManager.parameterInjectorRegistry().registerInjectionService(new CapabilityInjectionService(loader));

        commandManager.parserRegistry().registerNamedParserSupplier("node_value",
                p -> new NodeValueArgumentParser<>());

        annotationParser = new AnnotationParser<>(commandManager, EasCommandSender.class,
                p -> CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build());

        annotationParser.parse(new GlobalCommands(commandManager, sessionManager, sessionListener));
        annotationParser.parse(new SessionCommands(sessionManager));

        new AddonLoader(this, getClassLoader()).load();
    }

    @Override
    public void onDisable() {
        sessionManager.stopAllSessions();
    }

    public History getHistory(Player player) {
        return historyManager.getHistory(player);
    }

    public <T> T getCapability(Class<T> type) {
        return loader.get(type);
    }

    public CapabilityLoader getCapabilityLoader() {
        return loader;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    public CommandManager<EasCommandSender> getCommandManager() {
        return commandManager;
    }

    public AnnotationParser<EasCommandSender> getAnnotationParser() {
        return annotationParser;
    }
}
