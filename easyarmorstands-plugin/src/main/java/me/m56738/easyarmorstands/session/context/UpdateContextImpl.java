package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import org.jetbrains.annotations.NotNull;

public class UpdateContextImpl implements UpdateContext {
    private final EyeRay eyeRay;

    public UpdateContextImpl(EyeRay eyeRay) {
        this.eyeRay = eyeRay;
    }

    @Override
    public @NotNull EyeRay eyeRay() {
        return eyeRay;
    }
}
