package me.m56738.easyarmorstands.towny;

import com.palmergames.bukkit.towny.event.executors.TownyActionEventExecutor;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.region.RegionListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class TownyPrivilegeChecker extends RegionListener {
    @Override
    public boolean isAllowed(Player player, Location location, boolean silent) {
        return TownyActionEventExecutor.canSwitch(player, location, Material.BLAZE_ROD, silent);
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.TOWNY_BYPASS);
    }

    @Override
    public void sendCreateError(Player player, PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.towny.deny-create"));
    }

    @Override
    public void sendDestroyError(Player player, Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.towny.deny-destroy"));
    }

    @Override
    public void sendEditError(Player player, Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.towny.deny-select"));
    }
}
