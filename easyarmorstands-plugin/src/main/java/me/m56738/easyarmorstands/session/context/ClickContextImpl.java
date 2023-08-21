package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickContextImpl implements ClickContext {
    private final EyeRay eyeRay;
    private final Type type;
    private final Entity entity;
    private final Block block;

    public ClickContextImpl(EyeRay eyeRay, Type type, Entity entity, Block block) {
        this.eyeRay = eyeRay;
        this.type = type;
        this.entity = entity;
        this.block = block;
    }

    @Override
    public @NotNull EyeRay eyeRay() {
        return eyeRay;
    }

    @Override
    public @NotNull Type type() {
        return type;
    }

    @Override
    public @Nullable Entity entity() {
        return entity;
    }

    @Override
    public @Nullable Block block() {
        return block;
    }
}
