package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.lib.joml.Vector2dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.ComponentLike;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdateContextImpl implements UpdateContext {
    private final @NotNull SessionImpl session;
    private final @NotNull List<@NotNull Input> inputs = new ArrayList<>();
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

    @Override
    public void addInput(@NotNull Input input) {
        inputs.add(input);
    }

    public @NotNull List<@NotNull Input> getInputs() {
        return inputs;
    }
}
