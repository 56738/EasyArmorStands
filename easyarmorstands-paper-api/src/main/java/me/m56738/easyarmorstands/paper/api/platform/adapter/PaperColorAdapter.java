package me.m56738.easyarmorstands.paper.api.platform.adapter;

import me.m56738.easyarmorstands.api.util.Color;

public final class PaperColorAdapter {
    private PaperColorAdapter() {
    }

    public static Color fromNative(org.bukkit.Color nativeColor) {
        return Color.ofARGB(nativeColor.asARGB());
    }

    public static org.bukkit.Color toNative(Color color) {
        return org.bukkit.Color.fromARGB(color.value());
    }
}
