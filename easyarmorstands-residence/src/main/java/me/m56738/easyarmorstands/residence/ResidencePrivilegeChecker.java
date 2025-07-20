package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResidencePrivilegeChecker implements RegionPrivilegeChecker {
    private final Residence residencePlugin;

    public ResidencePrivilegeChecker(Residence residencePlugin) {
        this.residencePlugin = residencePlugin;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        try {
            FlagPermissions perms = residencePlugin.getPermsByLoc(location);
            return perms.playerHas(player, Flags.build, true);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.RESIDENCE_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.residence.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.residence.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.residence.deny-select"));
    }
}
