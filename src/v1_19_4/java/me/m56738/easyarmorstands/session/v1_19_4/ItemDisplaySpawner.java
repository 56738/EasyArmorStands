package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNodeFactory;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplaySpawner extends DisplaySpawner<ItemDisplay> {
    public ItemDisplaySpawner(Session session, DisplayAddon addon, DisplayRootNodeFactory<ItemDisplay> factory) {
        super(ItemDisplay.class, EntityType.ITEM_DISPLAY, session, addon, factory);
    }

    @Override
    public ItemDisplay spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return spawnCapability.spawnEntity(location, ItemDisplay.class, e -> {
            e.setItemStack(new ItemStack(Material.AIR));
        });
    }
}
