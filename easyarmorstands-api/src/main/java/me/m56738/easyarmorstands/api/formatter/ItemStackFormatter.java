package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class ItemStackFormatter implements ValueFormatter<ItemStack> {
    @Override
    public Component format(ItemStack value) {
        return value.displayName();
    }
}
