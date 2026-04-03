package me.m56738.easyarmorstands.addon;

public interface AddonFactory<T extends Addon> {
    boolean isEnabled();

    boolean isAvailable();

    default Priority getPriority() {
        return Priority.NORMAL;
    }

    T create();
}
