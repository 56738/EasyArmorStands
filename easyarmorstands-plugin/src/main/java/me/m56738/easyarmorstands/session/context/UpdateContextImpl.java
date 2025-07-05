package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.session.SessionImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2dc;

public class UpdateContextImpl implements UpdateContext {
    private final @NotNull SessionImpl session;
    private EyeRay eyeRay;
    private @NotNull Component actionBar = Component.empty();
    private @NotNull Component title = Component.empty();
    private @NotNull Component subtitle = Component.empty();

    public UpdateContextImpl(@NotNull SessionImpl session) {
        this.session = session;
    }

    @Override
    public @NotNull EyeRay eyeRay() {
        if (eyeRay == null) {
            eyeRay = session.eyeRay();
        }
        return eyeRay;
    }

    @Override
    public @NotNull EyeRay eyeRay(@NotNull Vector2dc cursor) {
        return session.eyeRay(cursor);
    }

    public @NotNull Component getActionBar() {
        return actionBar;
    }

    @Override
    public void setActionBar(@NotNull ComponentLike actionBar) {
        this.actionBar = actionBar.asComponent();
    }

    public @NotNull Component getTitle() {
        return title;
    }

    @Override
    public void setTitle(@NotNull ComponentLike title) {
        this.title = title.asComponent();
    }

    public @NotNull Component getSubtitle() {
        return subtitle;
    }

    @Override
    public void setSubtitle(@NotNull ComponentLike subtitle) {
        this.subtitle = subtitle.asComponent();
    }
}
