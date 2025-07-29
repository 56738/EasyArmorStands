package me.m56738.easyarmorstands.api.platform.entity.display;

public interface Brightness {
    static Brightness of(int block, int sky) {
        return new BrightnessImpl(block, sky);
    }

    int block();

    int sky();
}
