package me.m56738.easyarmorstands.addon.traincarts;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import org.bukkit.entity.Player;

public class TrainCartsIntegration {
    private final EasyArmorStands plugin;

    public TrainCartsIntegration(EasyArmorStands plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("eas model [query]")
    @CommandPermission("easyarmorstands.traincarts.model")
    public void openModelMenu(Player player, ArmorStandSession session, @Argument("query") @Greedy String query) {
        if (session == null) {
            return;
        }
        TrainCarts.plugin.getModelListing().buildDialog(player, plugin)
                .query(query != null ? query : "")
                .cancelOnRootRightClick()
                .show()
                .thenAccept(result -> {
                    if (result.cancelledWithRootRightClick()) {
                        session.openMenu();
                    } else if (result.success()) {
                        session.openMenu();
                        player.setItemOnCursor(result.selectedItem());
                    }
                });
    }
}
