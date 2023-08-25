package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.Command;
import cloud.commandframework.keys.SimpleCloudKey;
import cloud.commandframework.permission.PredicatePermission;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import net.kyori.adventure.key.Key;

import java.util.function.BiFunction;

public class PropertyPermissionBuilderModifier implements BiFunction<PropertyPermission, Command.Builder<EasCommandSender>, Command.Builder<EasCommandSender>> {
    @SuppressWarnings("PatternValidation")
    @Override
    public Command.Builder<EasCommandSender> apply(
            PropertyPermission annotation,
            Command.Builder<EasCommandSender> builder) {
        String value = annotation.value();
        Key key = Key.key(value);
        PropertyType<?> type = EasyArmorStandsPlugin.getInstance().propertyTypeRegistry().get(key);
        return builder.permission(PredicatePermission.of(
                SimpleCloudKey.of("property_permission_" + key.asString()),
                new PropertyPermissionPredicate(type)));
    }
}
