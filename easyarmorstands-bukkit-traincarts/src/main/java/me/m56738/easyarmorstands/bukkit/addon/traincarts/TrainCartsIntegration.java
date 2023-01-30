package me.m56738.easyarmorstands.bukkit.addon.traincarts;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.bukkit.EasyArmorStands;
import me.m56738.easyarmorstands.bukkit.platform.BukkitPlatform;
import me.m56738.easyarmorstands.bukkit.platform.BukkitPlayer;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import me.m56738.easyarmorstands.core.session.Session;
import org.bukkit.entity.Player;

public class TrainCartsIntegration {
    private final EasyArmorStands plugin;

    public TrainCartsIntegration(EasyArmorStands plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("eas model [query]")
    @CommandPermission("easyarmorstands.traincarts.model")
    public void openModelMenu(EasPlayer player, Session session, @Argument("query") @Greedy String query) {
        if (session == null) {
            return;
        }
        Player p = ((BukkitPlayer) player).get();
        TrainCarts.plugin.getModelListing().buildDialog(p, plugin)
                .query(query != null ? query : "")
                .cancelOnRootRightClick()
                .show()
                .thenAccept(result -> {
                    if (result.cancelledWithRootRightClick()) {
                        session.openMenu();
                    } else if (result.success()) {
                        session.openMenu();
                        p.setItemOnCursor(result.selectedItem());
                    }
                });
    }

    public BukkitPlatform getPlatform() {
        return plugin.getPlatform();
    }
}
