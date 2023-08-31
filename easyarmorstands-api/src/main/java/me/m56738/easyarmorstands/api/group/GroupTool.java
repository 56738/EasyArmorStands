package me.m56738.easyarmorstands.api.group;

public interface GroupTool {
    void revert();

    void commit();

    boolean isValid();
}
