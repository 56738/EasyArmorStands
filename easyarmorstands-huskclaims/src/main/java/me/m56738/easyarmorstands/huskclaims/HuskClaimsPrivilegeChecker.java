package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.region.RegionListener;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.libraries.cloplib.operation.Operation;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class HuskClaimsPrivilegeChecker extends RegionListener {
    private final BukkitHuskClaimsAPI api;
    private final OperationType operationType;

    public HuskClaimsPrivilegeChecker(BukkitHuskClaimsAPI api, OperationType operationType) {
        this.api = api;
        this.operationType = operationType;
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
    public void sendCreateError(Player player, PropertyContainer properties) {
    }

    @Override
    public void sendDestroyError(Player player, Element element) {
    }

    @Override
    public void sendEditError(Player player, Element element) {
    }
}
