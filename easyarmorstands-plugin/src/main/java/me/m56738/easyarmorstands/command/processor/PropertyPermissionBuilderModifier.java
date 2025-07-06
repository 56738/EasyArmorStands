package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.lib.cloud.Command;
import me.m56738.easyarmorstands.lib.cloud.annotations.BuilderModifier;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PropertyPermissionBuilderModifier implements BuilderModifier<PropertyPermission, Source> {
    @Override
    public Command.Builder<Source> modifyBuilder(PropertyPermission annotation, Command.Builder<Source> builder) {
        return builder.permission(PropertyPermissionPredicate.createPermission(annotation.value()));
    }
}
