package me.m56738.easyarmorstands.api.editor.axis;

public interface EditorAxis {
    void revert();

    void commit();

    boolean isValid();
}
