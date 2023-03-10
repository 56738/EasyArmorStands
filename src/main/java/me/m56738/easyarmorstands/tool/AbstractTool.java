package me.m56738.easyarmorstands.tool;

import net.kyori.adventure.text.Component;

public abstract class AbstractTool implements Tool {
    private final Component name;
    private final Component description;

    public AbstractTool(Component name, Component description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }
}
