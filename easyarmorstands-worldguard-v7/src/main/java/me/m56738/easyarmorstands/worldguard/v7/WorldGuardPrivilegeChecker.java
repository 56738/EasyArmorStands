package me.m56738.easyarmorstands.worldguard.v7;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldGuardPrivilegeChecker implements RegionPrivilegeChecker {
    private final SessionManager sessionManager;
    private final RegionContainer regionContainer;

    public WorldGuardPrivilegeChecker() {
        WorldGuardPlatform platform = WorldGuard.getInstance().getPlatform();
        this.sessionManager = platform.getSessionManager();
        this.regionContainer = platform.getRegionContainer();
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        if (sessionManager.hasBypass(localPlayer, BukkitAdapter.adapt(location.getWorld()))) {
            return true;
        }
        RegionQuery query = regionContainer.createQuery();
        return query.testState(BukkitAdapter.adapt(location), localPlayer, Flags.BUILD);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.WORLDGUARD_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.worldguard.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.worldguard.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.worldguard.deny-select"));
    }
}
