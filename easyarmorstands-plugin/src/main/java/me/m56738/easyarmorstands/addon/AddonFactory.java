package me.m56738.easyarmorstands.addon;

public interface AddonFactory<T extends Addon> {
    boolean isEnabled();

    boolean isAvailable();

    T create();
}
