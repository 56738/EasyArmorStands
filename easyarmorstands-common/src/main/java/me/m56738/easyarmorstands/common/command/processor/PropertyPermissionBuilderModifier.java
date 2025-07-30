package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.common.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import net.kyori.adventure.key.Key;
import org.incendo.cloud.Command;
import org.incendo.cloud.annotations.BuilderModifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PropertyPermissionBuilderModifier implements BuilderModifier<PropertyPermission, CommandSource> {
    private final PropertyTypeRegistry propertyTypeRegistry;

    public PropertyPermissionBuilderModifier(PropertyTypeRegistry propertyTypeRegistry) {
        this.propertyTypeRegistry = propertyTypeRegistry;
    }

    @SuppressWarnings("PatternValidation")
    @Override
    public Command.Builder<CommandSource> modifyBuilder(PropertyPermission annotation, Command.Builder<CommandSource> builder) {
        Key key = Key.key(annotation.value());
        PropertyType<?> propertyType = propertyTypeRegistry.get(key);
        return builder.permission(PropertyPermissionPredicate.createPermission(propertyType));
    }
}
