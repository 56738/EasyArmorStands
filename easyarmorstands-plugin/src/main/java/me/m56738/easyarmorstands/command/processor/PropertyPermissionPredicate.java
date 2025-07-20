package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.common.permission.Permissions;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.permission.Permission;
import org.incendo.cloud.permission.PredicatePermission;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class PropertyPermissionPredicate implements Predicate<Source> {
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
    public boolean test(Source sender) {
        if (sender instanceof PlayerSource playerSource) {
            Player player = playerSource.source();
            return player.hasPermission(Permissions.EDIT) && type.canChange(PaperPlayer.fromNative(player));
        }
        return false;
    }
}
