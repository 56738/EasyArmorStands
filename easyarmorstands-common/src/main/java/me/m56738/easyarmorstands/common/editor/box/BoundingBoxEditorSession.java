package me.m56738.easyarmorstands.common.editor.box;

import me.m56738.easyarmorstands.api.util.BoundingBox;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

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
