package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.EasPlayer;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TrainCartsModelListingSlot implements InventorySlot {
    private final TrainCartsIntegration integration;
    private final ArmorStandMenu menu;

    public TrainCartsModelListingSlot(TrainCartsIntegration integration, ArmorStandMenu menu) {
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
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (put) {
            // Delete items placed into this slot
            menu.queueTask(() -> menu.getSession().getPlayer().setItemOnCursor(null));
            return false;
        }
        EasPlayer player = new EasPlayer(menu.getSession().getPlayer(), menu.getSession().audience());
        menu.queueTask(() -> integration.openModelMenu(player, menu.getSession(), menu.getEntity(), ""));
        return false;
    }
}
