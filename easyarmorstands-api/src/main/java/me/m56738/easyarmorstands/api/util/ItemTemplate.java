package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.api.platform.inventory.Item;

import java.util.Locale;

public interface ItemTemplate {
    Item render(Locale locale);
}
