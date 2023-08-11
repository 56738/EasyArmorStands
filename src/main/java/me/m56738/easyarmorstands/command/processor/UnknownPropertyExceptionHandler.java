package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.CommandManager;
import cloud.commandframework.exceptions.CommandExecutionException;
import me.m56738.easyarmorstands.property.UnknownPropertyException;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.function.BiConsumer;

public class UnknownPropertyExceptionHandler<C extends Audience> implements BiConsumer<C, CommandExecutionException> {
    private final BiConsumer<C, CommandExecutionException> defaultHandler;

    public UnknownPropertyExceptionHandler(BiConsumer<C, CommandExecutionException> defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public static <C extends Audience> void register(CommandManager<C> commandManager) {
        commandManager.registerExceptionHandler(CommandExecutionException.class,
                new UnknownPropertyExceptionHandler<>(
                        commandManager.getExceptionHandler(CommandExecutionException.class)));
    }

    @Override
    public void accept(C sender, CommandExecutionException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof UnknownPropertyException) {
            UnknownPropertyException unknownPropertyException = (UnknownPropertyException) cause;
            sender.sendMessage(Component.text("You're not editing an entity which supports the ", NamedTextColor.RED)
                    .append(unknownPropertyException.getType().getDisplayName())
                    .append(Component.text(" property.")));
            return;
        }
        if (defaultHandler != null) {
            defaultHandler.accept(sender, exception);
        } else {
            throw exception;
        }
    }
}
