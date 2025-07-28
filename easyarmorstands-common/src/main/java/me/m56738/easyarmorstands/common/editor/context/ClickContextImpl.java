package me.m56738.easyarmorstands.common.editor.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.world.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ClickContextImpl(
        @NotNull EyeRay eyeRay,
        @NotNull Type type,
        @Nullable Entity entity,
        @Nullable Block block
) implements ClickContext {
}
