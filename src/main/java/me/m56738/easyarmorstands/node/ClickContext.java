package me.m56738.easyarmorstands.node;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickContext {
    private final ClickType type;
    private final Entity entity;
    private final Block block;

    public ClickContext(ClickType type, Entity entity, Block block) {
        this.type = type;
        this.entity = entity;
        this.block = block;
    }

    public @NotNull ClickType getType() {
        return type;
    }

    public @Nullable Entity getEntity() {
        return entity;
    }

    public @Nullable Block getBlock() {
        return block;
    }
}
