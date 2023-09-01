package me.m56738.easyarmorstands.api.group;

@Deprecated
public interface GroupTool {
    void revert();

    void commit();

    boolean isValid();
}
