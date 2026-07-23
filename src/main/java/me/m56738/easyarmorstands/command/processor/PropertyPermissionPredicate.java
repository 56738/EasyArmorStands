package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.key.Key;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.permission.Permission;
import org.incendo.cloud.permission.PredicatePermission;
import org.intellij.lang.annotations.Subst;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

@NullMarked
public class PropertyPermissionPredicate implements Predicate<EasCommandSender> {
    private final @Nullable EasyArmorStandsHolder holder;
    private final Key key;
    private @Nullable PropertyType<?> type;

    private PropertyPermissionPredicate(@Nullable EasyArmorStandsHolder holder, Key key, @Nullable PropertyType<?> type) {
        this.holder = holder;
        this.key = key;
        this.type = type;
    }

    public static Permission createPermission(EasyArmorStandsHolder holder, @Subst("n:v") String value) {
        return createPermission(holder, Key.key(value));
    }

    public static Permission createPermission(PropertyType<?> type) {
        return createPermission(null, type.key(), type);
    }

    public static Permission createPermission(EasyArmorStandsHolder holder, Key key) {
        return createPermission(holder, key, null);
    }

    private static Permission createPermission(@Nullable EasyArmorStandsHolder holder, Key key, @Nullable PropertyType<?> type) {
        return PredicatePermission.of(
                CloudKey.of("property_permission_" + key.asString()),
                new PropertyPermissionPredicate(holder, key, type));
    }

    @Override
    public boolean test(EasCommandSender sender) {
        if (type == null) {
            type = Objects.requireNonNull(holder).get().propertyTypeRegistry().get(key);
        }
        if (sender instanceof EasPlayer player) {
            return player.get().hasPermission(Permissions.EDIT) && type.canChange(player.get());
        }
        return false;
    }
}
