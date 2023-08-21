package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class EnterContextImpl implements EnterContext {
    private final @Nullable Vector3dc cursor;

    public EnterContextImpl(@Nullable Vector3dc cursor) {
        this.cursor = cursor;
    }

    @Override
    public @Nullable Vector3dc cursor() {
        return cursor;
    }
}
