package me.m56738.easyarmorstands.addon.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TrainCartsModelListingSlot implements MenuSlot {
    private final MenuElement element;

    public TrainCartsModelListingSlot(MenuElement element) {
        this.element = element;
    }

    @Override
    public ItemStack getItem() {
        return Util.createItem(
                ItemType.BOOK,
                Component.text("Open model browser", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Select a resource pack model from", NamedTextColor.GRAY),
                        Component.text("the TrainCarts model browser.", NamedTextColor.GRAY)
                )
        );
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
