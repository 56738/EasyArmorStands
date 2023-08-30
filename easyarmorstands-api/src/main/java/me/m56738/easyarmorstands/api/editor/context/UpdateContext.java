package me.m56738.easyarmorstands.api.editor.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2dc;

@ApiStatus.NonExtendable
public interface UpdateContext {
    @Contract(pure = true)
    @NotNull EyeRay eyeRay();

    @Contract(pure = true)
    @NotNull EyeRay eyeRay(@NotNull Vector2dc cursor);

    void setActionBar(@NotNull ComponentLike actionBar);

    void setTitle(@NotNull ComponentLike title);

    void setSubtitle(@NotNull ComponentLike subtitle);
}
