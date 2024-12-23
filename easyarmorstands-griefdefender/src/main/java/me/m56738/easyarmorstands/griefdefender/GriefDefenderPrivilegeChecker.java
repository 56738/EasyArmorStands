package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.Core;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.TrustTypes;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GriefDefenderPrivilegeChecker implements RegionPrivilegeChecker {
    private final Core core;

    public GriefDefenderPrivilegeChecker(Core core) {
        this.core = core;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        if (!core.isEnabled(location.getWorld().getUID())) {
            return true;
        }
        Claim claim = core.getClaimAt(location);
        if (claim == null || claim.isWilderness()) {
            return true;
        }
        return claim.isUserTrusted(player.getUniqueId(), TrustTypes.BUILDER);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.GRIEFDEFENDER_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.griefdefender.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.griefdefender.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        EasyArmorStandsPlugin.getInstance().getAdventure().player(player)
                .sendMessage(Message.error("easyarmorstands.error.griefdefender.deny-select"));
    }
}
