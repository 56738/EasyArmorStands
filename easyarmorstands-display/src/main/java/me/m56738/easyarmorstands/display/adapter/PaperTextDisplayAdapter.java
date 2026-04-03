package me.m56738.easyarmorstands.display.adapter;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.Nullable;

public class PaperTextDisplayAdapter extends TextDisplayAdapter {
    private static final PaperTextDisplayAdapter instance = new PaperTextDisplayAdapter();

    private PaperTextDisplayAdapter() {
    }

    public static @Nullable PaperTextDisplayAdapter getInstance() {
        return instance;
    }

    @Override
    public Component getText(TextDisplay entity) {
        return entity.text();
    }

    @Override
    public void setText(TextDisplay entity, Component text) {
        entity.text(text);
    }
}
