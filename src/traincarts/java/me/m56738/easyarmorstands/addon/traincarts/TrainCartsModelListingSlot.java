package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.SessionMenu;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TrainCartsModelListingSlot implements InventorySlot {
    private final TrainCartsIntegration integration;
    private final SessionMenu menu;

    public TrainCartsModelListingSlot(TrainCartsIntegration integration, SessionMenu menu) {
        this.integration = integration;
        this.menu = menu;
    }

    @Override
    public void initialize(int slot) {
        ItemStack item = Util.createItem(
                ItemType.BOOK,
                Component.text("Open model browser", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Select a resource pack model from", NamedTextColor.GRAY),
                        Component.text("the TrainCarts model browser.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        if (put) {
            // Delete items placed into this slot
            menu.queueTask(() -> menu.getSession().getPlayer().setItemOnCursor(null));
            return false;
        }
        menu.queueTask(() -> integration.openModelMenu(menu.getSession().getPlayer(), menu.getSession(), ""));
        return false;
    }
}
