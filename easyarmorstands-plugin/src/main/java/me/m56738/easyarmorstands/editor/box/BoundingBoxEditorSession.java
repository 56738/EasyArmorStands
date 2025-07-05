package me.m56738.easyarmorstands.editor.box;

import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public interface BoundingBoxEditorSession {
    BoundingBox getBoundingBox();

    boolean setCenter(Vector3dc center);

    float getWidth();

    boolean setWidth(float width);

    float getHeight();

    boolean setHeight(float height);

    void revert();

    void commit(@Nullable Component description);

    @Contract(pure = true)
    boolean isValid();
}
