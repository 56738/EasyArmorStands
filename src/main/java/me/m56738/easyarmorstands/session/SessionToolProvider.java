package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;

import java.util.Locale;

public interface SessionToolProvider {
    ItemStack createTool(Locale locale);

    boolean isTool(ItemStack item);
}
