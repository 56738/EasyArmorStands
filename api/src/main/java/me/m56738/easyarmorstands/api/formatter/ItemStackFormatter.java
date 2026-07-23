package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import net.kyori.adventure.text.Component;

public class ItemStackFormatter implements ValueFormatter<ItemStack> {
    @Override
    public Component format(ItemStack value) {
        return value.displayName();
    }
}
