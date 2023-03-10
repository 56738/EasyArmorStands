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
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.addon.AddonLoader;
import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.command.AudienceInjector;
import me.m56738.easyarmorstands.command.BoneArgumentParser;
import me.m56738.easyarmorstands.command.CapabilityInjectionService;
import me.m56738.easyarmorstands.command.GlobalCommands;
import me.m56738.easyarmorstands.command.NoSessionException;
import me.m56738.easyarmorstands.command.PipelineExceptionHandler;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.SessionInjector;
import me.m56738.easyarmorstands.command.SessionPreprocessor;
import me.m56738.easyarmorstands.command.ToolArgumentParser;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.tool.Tool;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;
import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private SessionManager sessionManager;
    private BukkitAudiences adventure;
    private PaperCommandManager<CommandSender> commandManager;
    private AnnotationParser<CommandSender> annotationParser;

    public static EasyArmorStands getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        loader.load();

        sessionManager = new SessionManager();
        adventure = BukkitAudiences.create(this);

        getServer().getPluginManager().registerEvents(new SessionListener(this, sessionManager, adventure), this);
        getServer().getScheduler().runTaskTimer(this, sessionManager::update, 0, 1);

        try {
            commandManager = new PaperCommandManager<>(
                    this,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            try {
                commandManager.registerBrigadier();
            } catch (BukkitCommandManager.BrigadierFailureException e) {
                getLogger().log(Level.WARNING, "Failed to register Brigadier mappings", e);
            }
        }

        new MinecraftExceptionHandler<CommandSender>()
                .withArgumentParsingHandler()
                .withInvalidSyntaxHandler()
                .withNoPermissionHandler()
                .withCommandExecutionHandler()
                .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SENDER,
                        (sender, e) -> Component.text("Only players can use this command", NamedTextColor.RED))
                .apply(commandManager, adventure::sender);

        commandManager.registerExceptionHandler(NoSessionException.class,
                (sender, e) -> adventure.sender(sender).sendMessage(NoSessionException.MESSAGE));

        commandManager.registerCommandPreProcessor(new SessionPreprocessor(sessionManager));

        PipelineExceptionHandler.register(commandManager);

        commandManager.parameterInjectorRegistry().registerInjector(
                Session.class, new SessionInjector<>(Session.class));

        commandManager.parameterInjectorRegistry().registerInjector(
                ArmorStandSession.class, new SessionInjector<>(ArmorStandSession.class));

        commandManager.parameterInjectorRegistry().registerInjector(
                Audience.class, new AudienceInjector(adventure));

        commandManager.parameterInjectorRegistry().registerInjectionService(new CapabilityInjectionService(loader, adventure));

        commandManager.parserRegistry().registerParserSupplier(
                TypeToken.get(Bone.class),
                p -> new BoneArgumentParser());

        commandManager.parserRegistry().registerParserSupplier(
                TypeToken.get(Tool.class),
                p -> new ToolArgumentParser());

        annotationParser = new AnnotationParser<>(commandManager, CommandSender.class,
                p -> CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build());

        annotationParser.parse(new GlobalCommands(commandManager, adventure));
        annotationParser.parse(new SessionCommands(sessionManager));

        new AddonLoader(this, getClassLoader()).load();
    }

    @Override
    public void onDisable() {
        sessionManager.stopAllSessions();
    }

    public <T> T getCapability(Class<T> type) {
        return loader.get(type);
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    public CommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }

    public AnnotationParser<CommandSender> getAnnotationParser() {
        return annotationParser;
    }
}
