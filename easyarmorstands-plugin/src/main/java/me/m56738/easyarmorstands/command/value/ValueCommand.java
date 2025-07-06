package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.description.Description;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.cloud.parser.ParserDescriptor;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface ValueCommand<T> extends Command.Builder.Applicable<Source> {
    @NotNull Component getDisplayName();

    @NotNull Permission getPermission();

    @NotNull ParserDescriptor<Source, T> getParser();

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
