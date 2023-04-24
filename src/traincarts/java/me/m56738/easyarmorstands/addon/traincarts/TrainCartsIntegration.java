package me.m56738.easyarmorstands.addon.traincarts;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.EasPlayer;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.ArmorStand;

public class TrainCartsIntegration {
    private final EasyArmorStands plugin;

    public TrainCartsIntegration(EasyArmorStands plugin) {
        this.plugin = plugin;
    }

    @CommandMethod("eas model [query]")
    @CommandPermission("easyarmorstands.traincarts.model")
    public void openModelMenu(EasPlayer player, Session session, ArmorStand entity, @Argument("query") @Greedy String query) {
        if (session == null) {
            return;
        }
        TrainCarts.plugin.getModelListing().buildDialog(player.get(), plugin)
                .query(query != null ? query : "")
                .cancelOnRootRightClick()
                .show()
                .thenAccept(result -> {
                    if (result.cancelledWithRootRightClick()) {
                        player.get().openInventory(new ArmorStandMenu(session, entity).getInventory());
                    } else if (result.success()) {
                        player.get().openInventory(new ArmorStandMenu(session, entity).getInventory());
                        player.get().setItemOnCursor(result.selectedItem());
                    }
                });
    }
}
