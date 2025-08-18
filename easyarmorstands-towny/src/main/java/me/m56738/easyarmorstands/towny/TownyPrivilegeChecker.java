package me.m56738.easyarmorstands.towny;

import com.palmergames.bukkit.towny.event.executors.TownyActionEventExecutor;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownyPrivilegeChecker implements RegionPrivilegeChecker {
    @Override
    public boolean isAllowed(Player player, Location location) {
        return isAllowed(player, location, true);
    }

    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
        return TownyActionEventExecutor.canSwitch(player, location, Material.BLAZE_ROD, silent);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.TOWNY_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player).sendMessage(
                Message.error("easyarmorstands.error.towny.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player).sendMessage(
                Message.error("easyarmorstands.error.towny.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player).sendMessage(
                Message.error("easyarmorstands.error.towny.deny-select"));
    }
}
