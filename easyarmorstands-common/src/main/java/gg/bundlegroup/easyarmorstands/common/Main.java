package gg.bundlegroup.easyarmorstands.common;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.services.types.ConsumerService;
import gg.bundlegroup.easyarmorstands.common.command.BoneArgumentParser;
import gg.bundlegroup.easyarmorstands.common.command.ManipulatorArgumentParser;
import gg.bundlegroup.easyarmorstands.common.command.NoSessionException;
import gg.bundlegroup.easyarmorstands.common.command.PipelineExceptionHandler;
import gg.bundlegroup.easyarmorstands.common.command.RequiresFeature;
import gg.bundlegroup.easyarmorstands.common.command.SessionInjector;
import gg.bundlegroup.easyarmorstands.common.command.SessionPreprocessor;
import gg.bundlegroup.easyarmorstands.common.bone.Bone;
import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.common.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.session.SessionListener;
import gg.bundlegroup.easyarmorstands.common.session.SessionManager;
import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.Closeable;
import java.util.Optional;

public class Main implements Closeable {
    private final SessionManager sessionManager;
    private final CommandManager<EasCommandSender> commandManager;
    private final AnnotationParser<EasCommandSender> annotationParser;

    public Main(EasPlatform platform) {
        sessionManager = new SessionManager(platform);

        platform.registerListener(new SessionListener(platform, sessionManager));
        platform.registerTickTask(sessionManager::update);

        commandManager = platform.commandManager();

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

        commandManager.registerCommandPreProcessor(new SessionPreprocessor(sessionManager));

        PipelineExceptionHandler.register(commandManager);

        commandManager.parameterInjectorRegistry().registerInjector(
                Session.class, new SessionInjector<>());

        commandManager.parserRegistry().registerParserSupplier(
                TypeToken.get(Bone.class),
                p -> new BoneArgumentParser());

        commandManager.parserRegistry().registerParserSupplier(
                TypeToken.get(Manipulator.class),
                p -> new ManipulatorArgumentParser());

        annotationParser = new AnnotationParser<>(commandManager, EasCommandSender.class,
                p -> SimpleCommandMeta.empty());

        annotationParser.registerBuilderModifier(RequiresFeature.class,
                (requiresFeature, builder) -> builder.meta(RequiresFeature.KEY, requiresFeature.value()));

        try {
            annotationParser.parseContainers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public CommandManager<EasCommandSender> getCommandManager() {
        return commandManager;
    }

    public AnnotationParser<EasCommandSender> getAnnotationParser() {
        return annotationParser;
    }

    @Override
    public void close() {
        sessionManager.stopAllSessions();
    }
}
