package gg.bundlegroup.easyarmorstands.bukkit.addon.traincarts;

import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlatform;
import gg.bundlegroup.easyarmorstands.core.inventory.InventorySlot;
import gg.bundlegroup.easyarmorstands.core.inventory.SessionMenu;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;
import gg.bundlegroup.easyarmorstands.core.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class TrainCartsModelListingSlot implements InventorySlot {
    private final TrainCartsIntegration integration;
    private final BukkitPlatform platform;
    private final SessionMenu menu;

    public TrainCartsModelListingSlot(TrainCartsIntegration integration, SessionMenu menu) {
        this.integration = integration;
        this.platform = integration.getPlatform();
        this.menu = menu;
    }

    @Override
    public void initialize(int slot) {
        EasItem item = platform.createItem(EasMaterial.BOOK,
                Component.text("Open model browser", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Select a resource pack model from", NamedTextColor.GRAY),
                        Component.text("the TrainCarts model browser.", NamedTextColor.GRAY)
                ));
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        if (put) {
            // Delete items placed into this slot
            menu.queueTask(() -> menu.getSession().getPlayer().setCursor(null));
            return false;
        }
        integration.openModelMenu(menu.getSession().getPlayer(), menu.getSession(), "");
        return false;
    }
}
