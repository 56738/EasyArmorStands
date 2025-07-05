package me.m56738.easyarmorstands.capability.component;

import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Capability(name = "Chat components")
public interface ComponentCapability {
    @Nullable Component getCustomName(@NotNull Entity entity);

    void setCustomName(@NotNull Entity entity, @Nullable Component name);

    void setDisplayName(@NotNull ItemMeta meta, @Nullable Component displayName);

    void setLore(@NotNull ItemMeta meta, @NotNull List<@NotNull Component> lore);

    @NotNull Component getItemDisplayName(@NotNull ItemStack item);

    @NotNull Inventory createInventory(InventoryHolder holder, int size, Component title);
}
