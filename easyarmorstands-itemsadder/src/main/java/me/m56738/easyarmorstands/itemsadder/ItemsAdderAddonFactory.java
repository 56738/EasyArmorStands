package me.m56738.easyarmorstands.itemsadder;

import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class ItemsAdderAddonFactory implements AddonFactory<ItemsAdderAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("ItemsAdder") != null;
    }

    @Override
    public ItemsAdderAddon create() {
        return new ItemsAdderAddon();
    }
}
