package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionPredicate;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.Command;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class PropertyCommand<T> implements ValueCommand<T> {
    private final String name;
    private final PropertyType<T> type;
    private final ParserDescriptor<EasCommandSender, T> parser;

    public PropertyCommand(String name, PropertyType<T> type, ParserDescriptor<EasCommandSender, T> parser) {
        this.name = name;
        this.type = type;
        this.parser = parser;
    }

    public String getName() {
        return name;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return type.getName();
    }

    @Override
    public @NotNull Permission getPermission() {
        return PropertyPermissionPredicate.createPermission(type);
    }

    @Override
    public @NotNull ParserDescriptor<EasCommandSender, T> getParser() {
        return parser;
    }

    @Override
    public @NotNull Description getShowDescription() {
        return RichDescription.of(Message.component("easyarmorstands.command.description.show-property", type.getName()));
    }

    @Override
    public @NotNull Description getSetterDescription() {
        return RichDescription.of(Message.component("easyarmorstands.command.description.set-property", type.getName()));
    }

    @Override
    public boolean isSupported(@NotNull PropertyContainer properties) {
        return properties.getOrNull(type) != null;
    }

    @Override
    public @NotNull T getValue(@NotNull PropertyContainer properties) {
        return properties.get(type).getValue();
    }

    @Override
    public boolean setValue(@NotNull PropertyContainer properties, @NotNull T value) {
        return properties.get(type).setValue(value);
    }

    @Override
    public @NotNull Component formatValue(@NotNull T value) {
        return type.getValueComponent(value);
    }

    @Override
    public @NotNull String formatCommand(@NonNull T value) {
        return "/eas " + name + " " + type.getValueString(value);
    }

    @Override
    public void sendSuccess(@NotNull Audience audience, @NonNull T value) {
        audience.sendMessage(Message.success("easyarmorstands.success.changed-property",
                type.getName(),
                type.getValueComponent(value)));
    }

    @Override
    public Command.@NotNull Builder<EasCommandSender> applyToCommandBuilder(Command.@NotNull Builder<EasCommandSender> builder) {
        return builder.literal(name);
    }
}
