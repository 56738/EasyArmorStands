package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisMoveButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.AxisRotateButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.AxisScaleButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.EditorButtonProvider;
import me.m56738.easyarmorstands.api.editor.button.MoveButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.ScaleButtonBuilder;
import me.m56738.easyarmorstands.editor.button.AxisMoveButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.AxisRotateButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.AxisScaleButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.MoveButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.ScaleButtonBuilderImpl;
import org.jetbrains.annotations.NotNull;

class EditorButtonProviderImpl implements EditorButtonProvider {
    private final Session session;

    EditorButtonProviderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull AxisMoveButtonBuilder axisMove() {
        return new AxisMoveButtonBuilderImpl(session);
    }

    @Override
    public @NotNull AxisScaleButtonBuilder axisScale() {
        return new AxisScaleButtonBuilderImpl(session);
    }

    @Override
    public @NotNull AxisRotateButtonBuilder axisRotate() {
        return new AxisRotateButtonBuilderImpl(session);
    }

    @Override
    public @NotNull MoveButtonBuilder move() {
        return new MoveButtonBuilderImpl(session);
    }

    @Override
    public @NotNull ScaleButtonBuilder scale() {
        return new ScaleButtonBuilderImpl(session);
    }
}
