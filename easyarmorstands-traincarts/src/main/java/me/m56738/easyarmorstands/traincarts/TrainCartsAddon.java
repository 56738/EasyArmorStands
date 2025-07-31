package me.m56738.easyarmorstands.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;

public class TrainCartsAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;

    public TrainCartsAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "TrainCarts";
    }

    @Override
    public void enable() {
        // TODO slot
    }

    @Override
    public void disable() {
    }

    @Override
    public void reload() {
    }

    public void open(Player player, MenuElement element) {
        TrainCarts.plugin.getModelListing().buildDialog(PaperPlayer.toNative(player), plugin)
                .cancelOnRootRightClick()
                .show()
                .thenAccept(result -> {
                    if (result.cancelledWithRootRightClick()) {
                        element.openMenu(player);
                    } else if (result.success()) {
                        element.openMenu(player);
                        PaperPlayer.toNative(player).setItemOnCursor(result.selectedItem());
                    }
                });
    }
}
