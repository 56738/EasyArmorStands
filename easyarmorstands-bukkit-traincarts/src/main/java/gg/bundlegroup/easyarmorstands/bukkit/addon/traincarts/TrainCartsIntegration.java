package gg.bundlegroup.easyarmorstands.bukkit.addon.traincarts;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import com.bergerkiller.bukkit.tc.TrainCarts;
import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlayer;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.session.SessionInventory;
import org.bukkit.entity.Player;

public class TrainCartsIntegration {
    private final EasyArmorStands plugin;

    public TrainCartsIntegration(EasyArmorStands plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("eas model [query]")
    @CommandPermission("easyarmorstands.traincarts")
    public void openModelMenu(EasPlayer player, Session session, @Argument("query") @Greedy String query) {
        if (session == null) {
            return;
        }
        Player p = ((BukkitPlayer) player).get();
        TrainCarts.plugin.getModelListing().buildDialog(p, plugin)
                .query(query != null ? query : "")
                .whenSelected(listedItemModel -> {
                    SessionInventory inventory = new SessionInventory(session, plugin.getPlatform());
                    player.openInventory(inventory.getInventory());
                    p.setItemOnCursor(listedItemModel.item());
                })
                .show();
    }
}
