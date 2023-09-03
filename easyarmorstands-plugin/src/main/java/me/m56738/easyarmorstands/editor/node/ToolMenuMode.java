package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;

public enum ToolMenuMode {
    LOCAL(Message.component("easyarmorstands.node.local")),
    GLOBAL(Message.component("easyarmorstands.node.global")),
    SCALE(Message.component("easyarmorstands.node.scale"));

    private final Component name;

    ToolMenuMode(Component name) {
        this.name = name;
    }

    public Component getName() {
        return name;
    }
}
