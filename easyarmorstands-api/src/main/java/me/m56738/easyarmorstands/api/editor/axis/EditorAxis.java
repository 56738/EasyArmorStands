package me.m56738.easyarmorstands.api.editor.axis;

@Deprecated
public interface EditorAxis {
    void revert();

    void commit();

    boolean isValid();
}
