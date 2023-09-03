package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

public class PropertyPermissionPredicate implements Predicate<EasCommandSender> {
    private final PropertyType<?> type;

    public PropertyPermissionPredicate(PropertyType<?> type) {
        this.type = type;
    }

    @Override
    public boolean test(EasCommandSender sender) {
        String permission = type.getPermission();
        if (permission != null) {
            CommandSender commandSender = sender.get();
            return commandSender.hasPermission(Permissions.EDIT)
                    && commandSender.hasPermission(permission);
        } else {
            return true;
        }
    }
}
