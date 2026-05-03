package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.input.Input;
import org.joml.Vector3dc;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ButtonHandlerContext {
    void addInput(Input input);

    Vector3dc cursor();
}
