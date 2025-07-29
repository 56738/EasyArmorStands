package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.Command;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;

public interface ValueCommand<T> extends Command.Builder.Applicable<CommandSource> {
    @NotNull Component getDisplayName();

    @NotNull Permission getPermission();

    @NotNull ParserDescriptor<CommandSource, T> getParser();

    @NotNull Description getShowDescription();

    @NotNull Description getSetterDescription();

    default boolean isSupported(@NotNull PropertyContainer properties) {
        return true;
    }

    @NotNull T getValue(@NotNull PropertyContainer properties);

    boolean setValue(@NotNull PropertyContainer properties, @NotNull T value);

    @NotNull Component formatValue(@NotNull T value);

    @NotNull String formatCommand(@NotNull T value);

    void sendSuccess(@NotNull Audience audience, @NotNull T value);

    default void sendFailure(@NotNull Audience audience, @NotNull T value) {
        audience.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
    }

    default void sendNotSupported(@NotNull Audience audience) {
        audience.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
    }
}
