package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.region.RegionListener;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class GriefPreventionPrivilegeChecker extends RegionListener {
    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
        String reason = GriefPrevention.instance.allowBuild(player, location, Material.ARMOR_STAND);
        return reason == null;
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.GRIEFPREVENTION_BYPASS);
    }

    @Override
    public void sendCreateError(Player player, PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.griefprevention.deny-create"));
    }

    @Override
    public void sendDestroyError(Player player, Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.griefprevention.deny-destroy"));
    }

    @Override
    public void sendEditError(Player player, Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.griefprevention.deny-select"));
    }
}
