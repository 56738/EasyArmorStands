package me.m56738.easyarmorstands.worldguard.v6;

import com.sk89q.worldguard.bukkit.WGBukkit;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldGuardPrivilegeChecker implements RegionPrivilegeChecker {
    @Override
    public boolean isAllowed(Player player, Location location) {
        return WGBukkit.getPlugin().canBuild(player, location);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.WORLDGUARD_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.worldguard.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.worldguard.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.worldguard.deny-select"));
    }
}
