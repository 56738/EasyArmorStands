package me.m56738.easyarmorstands.api.editor.util;

import net.kyori.adventure.text.Component;

public enum ToolMode {
    LOCAL(Component.translatable("easyarmorstands.node.local")),
    GLOBAL(Component.translatable("easyarmorstands.node.global")),
    SCALE(Component.translatable("easyarmorstands.node.scale"));

    private final Component name;

    ToolMode(Component name) {
        this.name = name;
    }

    public Component getName() {
        return name;
    }
}
