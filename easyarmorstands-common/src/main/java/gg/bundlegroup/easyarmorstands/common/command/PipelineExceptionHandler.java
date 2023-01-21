package gg.bundlegroup.easyarmorstands.common.command;

import cloud.commandframework.CommandManager;
import cloud.commandframework.exceptions.CommandExecutionException;
import cloud.commandframework.services.PipelineException;

import java.util.function.BiConsumer;

public class PipelineExceptionHandler<C> implements BiConsumer<C, CommandExecutionException> {
    private final CommandManager<C> commandManager;
    private final BiConsumer<C, CommandExecutionException> fallback;

    public PipelineExceptionHandler(CommandManager<C> commandManager, BiConsumer<C, CommandExecutionException> fallback) {
        this.commandManager = commandManager;
        this.fallback = fallback;
    }

    public static <C> void register(CommandManager<C> commandManager) {
        commandManager.registerExceptionHandler(
                CommandExecutionException.class,
                new PipelineExceptionHandler<>(
                        commandManager,
                        commandManager.getExceptionHandler(CommandExecutionException.class)
                )
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void accept(C sender, CommandExecutionException exception) {
        if (exception.getCause() instanceof PipelineException) {
            PipelineException pipelineException = (PipelineException) exception.getCause();
            if (pipelineException.getCause() instanceof Exception) {
                Exception cause = (Exception) pipelineException.getCause();
                BiConsumer handler = commandManager.getExceptionHandler(cause.getClass());
                if (handler != null) {
                    handler.accept(sender, cause);
                    return;
                }
            }
        }
        fallback.accept(sender, exception);
    }
}
