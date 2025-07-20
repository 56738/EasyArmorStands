package me.m56738.easyarmorstands.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownyPrivilegeChecker implements RegionPrivilegeChecker {
    private final TownyAPI towny;

    public TownyPrivilegeChecker(TownyAPI towny) {
        this.towny = towny;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        TownBlock block = towny.getTownBlock(location);
        if (block == null) {
            return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.towny.allowWilderness;
        }

        Resident resident = towny.getResident(player);
        if (resident == null) {
            return false;
        }

        if (block.hasResident(resident) || block.hasTrustedResident(resident)) {
            return true;
        }

        Town town = block.getTownOrNull();
        if (town == null) {
            return false;
        }

        return town.hasResident(resident);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.TOWNY_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.towny.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.towny.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.towny.deny-select"));
    }
}
