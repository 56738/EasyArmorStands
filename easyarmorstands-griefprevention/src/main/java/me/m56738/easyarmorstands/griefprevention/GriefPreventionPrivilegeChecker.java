package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GriefPreventionPrivilegeChecker implements RegionPrivilegeChecker {
    @Override
    public boolean isAllowed(Player player, Location location) {
        String reason = GriefPrevention.instance.allowBuild(player, location, Material.ARMOR_STAND);
        return reason == null;
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.GRIEFPREVENTION_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.griefprevention.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.griefprevention.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.griefprevention.deny-select"));
    }
}
