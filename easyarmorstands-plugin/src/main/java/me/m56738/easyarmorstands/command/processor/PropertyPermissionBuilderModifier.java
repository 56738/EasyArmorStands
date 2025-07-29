package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.Command;
import org.incendo.cloud.annotations.BuilderModifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PropertyPermissionBuilderModifier implements BuilderModifier<PropertyPermission, CommandSource> {
    @Override
    public Command.Builder<CommandSource> modifyBuilder(PropertyPermission annotation, Command.Builder<CommandSource> builder) {
        return builder.permission(PropertyPermissionPredicate.createPermission(annotation.value()));
    }
}
