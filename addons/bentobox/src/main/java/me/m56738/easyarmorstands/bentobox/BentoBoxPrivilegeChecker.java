package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.api.flags.FlagListener;
import world.bentobox.bentobox.lists.Flags;

public class BentoBoxPrivilegeChecker extends FlagListener implements RegionPrivilegeChecker {
    @Override
    public boolean isAllowed(Player player, Location location) {
        return isAllowed(player, location, true);
    }

    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
        BentoBoxActionEvent event = new BentoBoxActionEvent();
        return checkIsland(event, player, location, Flags.ARMOR_STAND, silent);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.BENTOBOX_BYPASS);
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
