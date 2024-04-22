package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.annotations.BuilderModifier;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.permission.PredicatePermission;

public class PropertyPermissionBuilderModifier implements BuilderModifier<PropertyPermission, EasCommandSender> {
    @SuppressWarnings("PatternValidation")
    @Override
    public Command.Builder<? extends EasCommandSender> modifyBuilder(@NonNull PropertyPermission annotation, Command.Builder<EasCommandSender> builder) {
        String value = annotation.value();
        Key key = Key.key(value);
        PropertyType<?> type = EasyArmorStandsPlugin.getInstance().propertyTypeRegistry().get(key);
        return builder.permission(PredicatePermission.of(
                CloudKey.of("property_permission_" + key.asString()),
                new PropertyPermissionPredicate(type)));
    }
}
