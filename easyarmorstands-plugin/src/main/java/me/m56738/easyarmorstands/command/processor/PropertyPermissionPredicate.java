package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.permission.Permission;
import org.incendo.cloud.permission.PredicatePermission;
import org.intellij.lang.annotations.Subst;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

@NullMarked
public class PropertyPermissionPredicate implements Predicate<EasCommandSender> {
    private final Key key;
    private @Nullable PropertyType<?> type;

    private PropertyPermissionPredicate(Key key, @Nullable PropertyType<?> type) {
        this.key = key;
        this.type = type;
    }

    public static Permission createPermission(@Subst("n:v") String value) {
        return createPermission(Key.key(value));
    }

    public static Permission createPermission(PropertyType<?> type) {
        return createPermission(type.key(), type);
    }

    public static Permission createPermission(Key key) {
        return createPermission(key, null);
    }

    private static Permission createPermission(Key key, @Nullable PropertyType<?> type) {
        return PredicatePermission.of(
                CloudKey.of("property_permission_" + key.asString()),
                new PropertyPermissionPredicate(key, type));
    }

    @Override
    public boolean test(EasCommandSender sender) {
        if (type == null) {
            type = EasyArmorStandsPlugin.getInstance().propertyTypeRegistry().get(key);
        }
        if (sender instanceof EasPlayer) {
            Player player = ((EasPlayer) sender).player();
            return player.hasPermission(Permissions.EDIT) && type.canChange(player);
        }
        return false;
    }
}
