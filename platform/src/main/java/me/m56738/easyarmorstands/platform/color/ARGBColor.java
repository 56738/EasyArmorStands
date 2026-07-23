package me.m56738.easyarmorstands.platform.color;

import net.kyori.adventure.util.ARGBLike;
import org.jetbrains.annotations.Range;

public interface ARGBColor extends ARGBLike {
    ARGBColor WHITE = ARGBColor.of(0xFF000000);

    static ARGBColor of(int value) {
        return new ARGBColorImpl(value);
    }

    static ARGBColor of(ARGBLike value) {
        return ARGBColor.of(value.alpha(), value.red(), value.green(), value.blue());
    }

    static ARGBColor of(int alpha, int red, int green, int blue) {
        return new ARGBColorImpl((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
    }

    int value();

    @Override
    @Range(from = 0L, to = 255L)
    default int alpha() {
        return (value() >> 24) & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int red() {
        return (value() >> 16) & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int green() {
        return (value() >> 8) & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int blue() {
        return value() & 0xFF;
    }

    default ARGBColor withAlpha(int alpha) {
        return new ARGBColorImpl(value() & 0x00FFFFFF | (alpha & 0xFF) << 24);
    }

    default ARGBColor withRed(int red) {
        return new ARGBColorImpl(value() & 0xFF00FFFF | (red & 0xFF) << 16);
    }

    default ARGBColor withGreen(int green) {
        return new ARGBColorImpl(value() & 0xFFFF00FF | (green & 0xFF) << 8);
    }

    default ARGBColor withBlue(int blue) {
        return new ARGBColorImpl(value() & 0xFFFFFF00 | blue & 0xFF);
    }
}
