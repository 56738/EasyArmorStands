package me.m56738.easyarmorstands.lands;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.land.Area;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LandsPrivilegeChecker implements RegionPrivilegeChecker {
    private final LandsIntegration integration;
    private final RoleFlag flag;

    public LandsPrivilegeChecker(LandsIntegration integration, RoleFlag flag) {
        this.integration = integration;
        this.flag = flag;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        if (!location.getChunk().isLoaded()) {
            return false;
        }
        Area area = integration.getArea(location);
        if (area == null) {
            return true;
        }
        return area.hasRoleFlag(player.getUniqueId(), flag);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.LANDS_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.lands.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.lands.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.lands.deny-select"));
    }
}
