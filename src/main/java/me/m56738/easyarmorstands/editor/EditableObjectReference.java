package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.history.EntityReplacementListener;
import org.jetbrains.annotations.Nullable;

public interface EditableObjectReference extends EntityReplacementListener {
    @Nullable EditableObject restoreObject();
}
