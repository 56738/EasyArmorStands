package me.m56738.easyarmorstands.addon.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class TrainCartsModelListingSlot implements MenuSlot {
    private final TrainCartsAddon addon;
    private final MenuElement element;

    public TrainCartsModelListingSlot(TrainCartsAddon addon, MenuElement element) {
        this.addon = addon;
        this.element = element;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return addon.getBrowserButtonTemplate().render(locale);
    }

    @Override
    public void onClick(MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }
        Player player = click.player();
        click.queueTask(() -> {
            player.setItemOnCursor(null);
            TrainCarts.plugin.getModelListing().buildDialog(player, EasyArmorStands.getInstance())
                    .cancelOnRootRightClick()
                    .show()
                    .thenAccept(result -> {
                        if (result.cancelledWithRootRightClick()) {
                            element.openMenu(player);
                        } else if (result.success()) {
                            element.openMenu(player);
                            player.setItemOnCursor(result.selectedItem());
                        }
                    });
        });
    }
}
