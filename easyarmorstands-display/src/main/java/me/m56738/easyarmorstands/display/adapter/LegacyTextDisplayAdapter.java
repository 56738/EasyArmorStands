package me.m56738.easyarmorstands.display.adapter;

import me.m56738.easyarmorstands.lib.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.TextDisplay;

public class LegacyTextDisplayAdapter extends TextDisplayAdapter {
    private static final LegacyTextDisplayAdapter instance = new LegacyTextDisplayAdapter();
    private final LegacyComponentSerializer serializer = BukkitComponentSerializer.legacy();

    private LegacyTextDisplayAdapter() {
    }

    public static LegacyTextDisplayAdapter getInstance() {
        return instance;
    }

    @Override
    public Component getText(TextDisplay entity) {
        return serializer.deserializeOr(entity.getText(), Component.empty());
    }

    @Override
    public void setText(TextDisplay entity, Component text) {
        entity.setText(serializer.serializeOrNull(text));
    }
}
