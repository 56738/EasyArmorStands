package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.InventoryFactory;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;

public class PaperInventoryFactory implements InventoryFactory {
    private final Server server;

    public PaperInventoryFactory(Server server) {
        this.server = server;
    }

    @Override
    public Inventory createInventory(InventoryHolder holder, Component title, int size) {
        return PaperInventory.fromNative(server.createInventory(PaperInventoryHolder.toNative(holder), size, title));
    }
}
