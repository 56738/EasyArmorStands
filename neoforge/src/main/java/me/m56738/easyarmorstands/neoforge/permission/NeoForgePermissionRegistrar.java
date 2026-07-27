package me.m56738.easyarmorstands.neoforge.permission;

import me.m56738.easyarmorstands.permission.PermissionRegistrar;
import me.m56738.easyarmorstands.platform.neoforge.NeoForgePermissionManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class NeoForgePermissionRegistrar implements PermissionRegistrar {
    private final NeoForgePermissionManager permissionManager;

    public NeoForgePermissionRegistrar(NeoForgePermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    @Override
    public void registerPermission(String name, String description, Map<String, Boolean> children) {
        int i = name.indexOf('.');
        String modId = name.substring(0, i);
        String nodeName = name.substring(i + 1);
        permissionManager.register(new PermissionNode<>(modId, nodeName, PermissionTypes.BOOLEAN, this::resolveDefault));
    }

    private Boolean resolveDefault(@Nullable ServerPlayer player, UUID uuid, PermissionDynamicContext<?>... contexts) {
        if (player == null) {
            return false;
        }
        return player.permissions().hasPermission(new Permission.HasCommandLevel(player.level().getServer().operatorUserPermissions().level()));
    }
}
