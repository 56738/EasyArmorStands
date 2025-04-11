package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.libraries.cloplib.operation.Operation;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HuskClaimsPrivilegeChecker implements RegionPrivilegeChecker {
    private final BukkitHuskClaimsAPI api;
    private final OperationType operationType;

    public HuskClaimsPrivilegeChecker(BukkitHuskClaimsAPI api, OperationType operationType) {
        this.api = api;
        this.operationType = operationType;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        return isAllowed(player, location, true);
    }

    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
        return api.isOperationAllowed(Operation.of(
                api.getOnlineUser(player),
                operationType,
                api.getPosition(location),
                silent
        ));
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.HUSKCLAIMS_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
    }
}
