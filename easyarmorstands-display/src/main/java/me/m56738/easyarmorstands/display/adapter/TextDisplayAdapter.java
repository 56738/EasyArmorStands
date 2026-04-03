package me.m56738.easyarmorstands.display.adapter;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

public abstract class TextDisplayAdapter {
    private static final TextDisplayAdapter instance = initialize();

    public static TextDisplayAdapter getInstance() {
        return instance;
    }

    private static TextDisplayAdapter initialize() {
        TextDisplayAdapter adapter = PaperTextDisplayAdapter.getInstance();
        if (adapter != null) {
            return adapter;
        } else {
            return LegacyTextDisplayAdapter.getInstance();
        }
    }

    public abstract Component getText(TextDisplay entity);

    public abstract void setText(TextDisplay entity, Component text);
}
