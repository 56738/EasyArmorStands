package me.m56738.easyarmorstands.api.editor.context;

import me.m56738.easyarmorstands.api.editor.input.Input;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface LateUpdateContext {
    void setActionBar(@NotNull ComponentLike actionBar);

    void setTitle(@NotNull ComponentLike title);

    void setSubtitle(@NotNull ComponentLike subtitle);

    boolean isInputAvailable(@NotNull Input input);
}
