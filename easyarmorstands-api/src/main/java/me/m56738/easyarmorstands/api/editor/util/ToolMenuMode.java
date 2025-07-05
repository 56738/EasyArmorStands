package me.m56738.easyarmorstands.api.editor.util;

import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;

public enum ToolMenuMode {
    LOCAL(Component.translatable("easyarmorstands.node.local")),
    GLOBAL(Component.translatable("easyarmorstands.node.global")),
    SCALE(Component.translatable("easyarmorstands.node.scale"));

    private final Component name;

    ToolMenuMode(Component name) {
        this.name = name;
    }

    public Component getName() {
        return name;
    }
}
