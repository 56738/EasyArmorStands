package me.m56738.easyarmorstands.paper.listener;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import org.bukkit.block.Crafter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PaperToolListener implements Listener {
    private final EasyArmorStandsCommon eas;

    public PaperToolListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (eas.sessionToolProvider().isTool(PaperItemStack.fromNative(event.getFuel()))) {
            event.setBurnTime(0);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (eas.sessionToolProvider().isTool(PaperItemStack.fromNative(item))) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent event) {
        if (event.getBlock().getState(false) instanceof Crafter crafter) {
            for (ItemStack item : crafter.getInventory()) {
                if (eas.sessionToolProvider().isTool(PaperItemStack.fromNative(item))) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
}
