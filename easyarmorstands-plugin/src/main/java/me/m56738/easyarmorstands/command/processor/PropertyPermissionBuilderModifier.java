package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.annotations.BuilderModifier;

public class PropertyPermissionBuilderModifier implements BuilderModifier<PropertyPermission, EasCommandSender> {
    @Override
    public Command.@NonNull Builder<? extends EasCommandSender> modifyBuilder(@NonNull PropertyPermission annotation, Command.Builder<EasCommandSender> builder) {
        return builder.permission(PropertyPermissionPredicate.createPermission(annotation.value()));
    }
}
