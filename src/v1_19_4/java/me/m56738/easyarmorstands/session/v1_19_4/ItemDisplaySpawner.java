package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplaySpawner extends DisplaySpawner<ItemDisplay> {
    public ItemDisplaySpawner() {
        super(ItemDisplay.class, EntityType.ITEM_DISPLAY);
    }

    @Override
    public ItemDisplay spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return spawnCapability.spawnEntity(location, ItemDisplay.class, e -> {
            e.setItemStack(new ItemStack(Material.AIR));
        });
    }
}
