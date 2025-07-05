package me.m56738.easyarmorstands.command.value;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionPredicate;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.parser.ParserDescriptor;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public abstract class PropertyCommand<T> implements ValueCommand<T> {
    private final PropertyType<T> type;
    private final ParserDescriptor<EasCommandSender, T> parser;

    public PropertyCommand(PropertyType<T> type, ParserDescriptor<EasCommandSender, T> parser) {
        this.type = type;
        this.parser = parser;
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
}
