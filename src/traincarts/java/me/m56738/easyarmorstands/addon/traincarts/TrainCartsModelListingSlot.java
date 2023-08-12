package me.m56738.easyarmorstands.addon.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
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
        click.cancel();
        // TODO Delete items placed into this slot
        Player player = click.player();
        click.queueTask(() -> TrainCarts.plugin.getModelListing().buildDialog(player, EasyArmorStands.getInstance())
                .cancelOnRootRightClick()
                .show()
                .thenAccept(result -> {
                    if (result.cancelledWithRootRightClick()) {
                        element.openMenu(player);
                    } else if (result.success()) {
                        element.openMenu(player);
                        player.setItemOnCursor(result.selectedItem());
                    }
                }));
    }
}
