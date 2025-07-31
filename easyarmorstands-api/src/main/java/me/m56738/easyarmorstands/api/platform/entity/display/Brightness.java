package me.m56738.easyarmorstands.api.platform.entity.display;

public interface Brightness {
    static Brightness of(int block, int sky) {
        return new BrightnessImpl(block, sky);
    }

    int block();

    int sky();

    default Brightness withBlock(int block) {
        return Brightness.of(block, sky());
    }

    default Brightness withSky(int sky) {
        return Brightness.of(block(), sky);
    }
}
