package me.m56738.easyarmorstands.api.editor.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.world.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface ClickContext {
    @Contract(pure = true)
    @NotNull EyeRay eyeRay();

    @Contract(pure = true)
    @NotNull Type type();

    @Contract(pure = true)
    @Nullable Entity entity();

    @Contract(pure = true)
    @Nullable Block block();

    enum Type {
        LEFT_CLICK,
        RIGHT_CLICK,
        SWAP_HANDS
    }
}
