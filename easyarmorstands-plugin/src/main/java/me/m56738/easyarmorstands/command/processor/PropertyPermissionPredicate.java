package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.lib.cloud.key.CloudKey;
import me.m56738.easyarmorstands.lib.cloud.permission.Permission;
import me.m56738.easyarmorstands.lib.cloud.permission.PredicatePermission;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class PropertyPermissionPredicate implements Predicate<EasCommandSender> {
    private final PropertyType<?> type;

    public PropertyPermissionPredicate(PropertyType<?> type) {
        this.type = type;
    }

    public static @NotNull Permission createPermission(@Subst("n:v") String value) {
        return createPermission(Key.key(value));
    }

    public static @NotNull Permission createPermission(Key key) {
        return createPermission(EasyArmorStandsPlugin.getInstance().propertyTypeRegistry().get(key));
    }

    public static @NotNull Permission createPermission(PropertyType<?> type) {
        return PredicatePermission.of(
                CloudKey.of("property_permission_" + type.key().asString()),
                new PropertyPermissionPredicate(type));
    }

    @Override
    public boolean test(EasCommandSender sender) {
        if (sender instanceof EasPlayer) {
            Player player = ((EasPlayer) sender).player();
            return player.hasPermission(Permissions.EDIT) && type.canChange(player);
        }
        return false;
    }
}
