package me.m56738.easyarmorstands.addon.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
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
        EasPlayer player = click.player();
        click.queueTask(() -> {
            player.get().setItemOnCursor(null);
            TrainCarts.plugin.getModelListing().buildDialog(player.get(), EasyArmorStands.getInstance())
                    .cancelOnRootRightClick()
                    .show()
                    .thenAccept(result -> {
                        if (result.cancelledWithRootRightClick()) {
                            element.openMenu(player);
                        } else if (result.success()) {
                            element.openMenu(player);
                            player.get().setItemOnCursor(result.selectedItem());
                        }
                    });
        });
    }
}
