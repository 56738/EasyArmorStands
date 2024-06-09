package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class PropertyPermissionPredicate implements Predicate<EasCommandSender> {
    private final PropertyType<?> type;

    public PropertyPermissionPredicate(PropertyType<?> type) {
        this.type = type;
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
