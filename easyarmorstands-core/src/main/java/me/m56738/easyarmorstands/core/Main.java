package me.m56738.easyarmorstands.core;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.services.types.ConsumerService;
import me.m56738.easyarmorstands.core.bone.Bone;
import me.m56738.easyarmorstands.core.command.BoneArgumentParser;
import me.m56738.easyarmorstands.core.command.NoSessionException;
import me.m56738.easyarmorstands.core.command.PipelineExceptionHandler;
import me.m56738.easyarmorstands.core.command.RequiresFeature;
import me.m56738.easyarmorstands.core.command.SessionInjector;
import me.m56738.easyarmorstands.core.command.SessionPreprocessor;
import me.m56738.easyarmorstands.core.command.ToolArgumentParser;
import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import me.m56738.easyarmorstands.core.platform.EasFeature;
import me.m56738.easyarmorstands.core.platform.EasPlatform;
import me.m56738.easyarmorstands.core.session.Session;
import me.m56738.easyarmorstands.core.session.SessionListener;
import me.m56738.easyarmorstands.core.session.SessionManager;
import me.m56738.easyarmorstands.core.tool.Tool;
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

        commandManager.parameterInjectorRegistry().registerInjector(
                SessionManager.class, (context, annotationAccessor) -> sessionManager);

        commandManager.parserRegistry().registerParserSupplier(
                TypeToken.get(Bone.class),
                p -> new BoneArgumentParser());

        commandManager.parserRegistry().registerParserSupplier(
                TypeToken.get(Tool.class),
                p -> new ToolArgumentParser());

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
