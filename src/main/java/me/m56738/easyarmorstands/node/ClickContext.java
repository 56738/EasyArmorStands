package me.m56738.easyarmorstands.node;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickContext {
    private final ClickType type;
    private final Entity entity;

    public ClickContext(ClickType type, Entity entity) {
        this.type = type;
        this.entity = entity;
    }

    public @NotNull ClickType getType() {
        return type;
    }

    public @Nullable Entity getEntity() {
        return entity;
    }
}
