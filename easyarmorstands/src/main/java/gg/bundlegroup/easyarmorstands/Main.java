package gg.bundlegroup.easyarmorstands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.services.types.ConsumerService;
import gg.bundlegroup.easyarmorstands.command.NoSessionException;
import gg.bundlegroup.easyarmorstands.command.PipelineExceptionHandler;
import gg.bundlegroup.easyarmorstands.command.RequiresFeature;
import gg.bundlegroup.easyarmorstands.command.SessionInjector;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.session.Session;
import gg.bundlegroup.easyarmorstands.session.SessionListener;
import gg.bundlegroup.easyarmorstands.session.SessionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.Closeable;
import java.util.Optional;

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

        commandManager.registerCommandPostProcessor(context -> {
            Optional<EasFeature> optional = context.getCommand().getCommandMeta().get(RequiresFeature.KEY);
            if (optional.isPresent()) {
                EasFeature feature = optional.get();
                if (!platform.hasFeature(feature)) {
                    context.getCommandContext().getSender().sendMessage(Component.text()
                            .content("Your server doesn't support this feature")
                            .hoverEvent(Component.text(feature.name()))
                            .color(NamedTextColor.RED));
                    ConsumerService.interrupt();
                }
            }
        });

        PipelineExceptionHandler.register(commandManager);

        commandManager.parameterInjectorRegistry().registerInjector(Session.class,
                new SessionInjector<>(sessionManager));

        AnnotationParser<EasCommandSender> parser = new AnnotationParser<>(commandManager, EasCommandSender.class,
                p -> SimpleCommandMeta.empty());

        parser.registerBuilderModifier(RequiresFeature.class,
                (requiresFeature, builder) -> builder.meta(RequiresFeature.KEY, requiresFeature.value()));

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
