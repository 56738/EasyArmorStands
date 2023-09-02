package me.m56738.easyarmorstands.api.editor.tool;

public interface AxisToolSession extends ToolSession {
    void setChange(double change);

    default void setValue(double value) {
        setChange(value);
    }
}
