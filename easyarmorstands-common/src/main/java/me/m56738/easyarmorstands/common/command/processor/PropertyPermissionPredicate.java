package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.permission.Permission;
import org.incendo.cloud.permission.PredicatePermission;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class PropertyPermissionPredicate implements Predicate<CommandSource> {
    private final PropertyType<?> type;

    public PropertyPermissionPredicate(PropertyType<?> type) {
        this.type = type;
    }

    public static @NotNull Permission createPermission(PropertyType<?> type) {
        return PredicatePermission.of(
                CloudKey.of("property_permission_" + type.key().asString()),
                new PropertyPermissionPredicate(type));
    }

    @Override
    public boolean test(CommandSource sender) {
        if (sender instanceof PlayerCommandSource playerSource) {
            Player player = playerSource.source();
            return player.hasPermission(Permissions.EDIT) && type.canChange(player);
        }
        return false;
    }
}
