package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2dc;
import org.joml.Vector3dc;

public class EnterContextImpl implements EnterContext {
    private final @NotNull SessionImpl session;
    private final @Nullable Vector3dc cursor;
    private EyeRay eyeRay;

    public EnterContextImpl(@NotNull SessionImpl session, @Nullable Vector3dc cursor) {
        this.session = session;
        this.cursor = cursor;
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

    @Override
    public @Nullable Vector3dc cursor() {
        return cursor;
    }
}
