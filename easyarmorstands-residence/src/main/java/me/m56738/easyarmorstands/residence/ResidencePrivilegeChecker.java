package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.region.RegionListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ResidencePrivilegeChecker extends RegionListener {
    private final Residence residencePlugin;

    public ResidencePrivilegeChecker(Residence residencePlugin) {
        this.residencePlugin = residencePlugin;
    }

    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
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
    public void sendCreateError(Player player, PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.residence.deny-create"));
    }

    @Override
    public void sendDestroyError(Player player, Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.residence.deny-destroy"));
    }

    @Override
    public void sendEditError(Player player, Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.residence.deny-select"));
    }
}
