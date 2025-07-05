package me.m56738.easyarmorstands.api.menu;

public interface MenuNotifier {
    void addSubmitAction(Runnable action);

    void addCommitAction(Runnable action);
}
