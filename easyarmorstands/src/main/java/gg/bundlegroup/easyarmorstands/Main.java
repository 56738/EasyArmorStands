package gg.bundlegroup.easyarmorstands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import gg.bundlegroup.easyarmorstands.command.NoSessionException;
import gg.bundlegroup.easyarmorstands.command.PipelineExceptionHandler;
import gg.bundlegroup.easyarmorstands.command.SessionInjector;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.Closeable;

public class Main implements Closeable {
    private final SessionManager sessionManager;

    public Main(EasPlatform platform) {
        sessionManager = new SessionManager();

        platform.registerListener(new SessionListener(sessionManager));
        platform.registerTickTask(sessionManager::update);

        CommandManager<EasCommandSender> commandManager = platform.commandManager();

        new MinecraftExceptionHandler<EasCommandSender>()
                .withArgumentParsingHandler()
                .withInvalidSyntaxHandler()
                .withNoPermissionHandler()
                .withCommandExecutionHandler()
                .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SENDER,
                        (sender, e) -> Component.text("Only players can use this command", NamedTextColor.RED))
                .apply(commandManager, sender -> sender);

        commandManager.registerExceptionHandler(NoSessionException.class,
                (sender, e) -> sender.sendMessage(NoSessionException.MESSAGE));

        PipelineExceptionHandler.register(commandManager);

        commandManager.parameterInjectorRegistry().registerInjector(Session.class,
                new SessionInjector<>(sessionManager));

        AnnotationParser<EasCommandSender> parser = new AnnotationParser<>(commandManager, EasCommandSender.class,
                p -> SimpleCommandMeta.empty());

        try {
            parser.parseContainers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    @Override
    public void close() {
        sessionManager.stopAllSessions();
    }
}
