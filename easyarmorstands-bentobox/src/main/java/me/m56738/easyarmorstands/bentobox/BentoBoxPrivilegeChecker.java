package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.region.RegionListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import world.bentobox.bentobox.api.flags.FlagListener;
import world.bentobox.bentobox.lists.Flags;

@NullMarked
public class BentoBoxPrivilegeChecker extends RegionListener {
    private final PrivilegeChecker checker = new PrivilegeChecker();

    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
        return checker.isAllowed(player, location, silent);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.BENTOBOX_BYPASS);
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

    private static class PrivilegeChecker extends FlagListener {
        public boolean isAllowed(Player player, Location location, boolean silent) {
            BentoBoxActionEvent event = new BentoBoxActionEvent();
            return checkIsland(event, player, location, Flags.ARMOR_STAND, silent);
        }
    }
}
