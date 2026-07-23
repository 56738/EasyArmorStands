package me.m56738.easyarmorstands.platform.color;

import net.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.Range;

public interface RGBColor extends RGBLike {
    RGBColor RED = RGBColor.of(0xFF0000);
    RGBColor GREEN = RGBColor.of(0x00FF00);
    RGBColor BLUE = RGBColor.of(0x0000FF);
    RGBColor WHITE = RGBColor.of(0xFFFFFF);

    static RGBColor of(int value) {
        return new RGBColorImpl(value & 0xFFFFFF);
    }

    static RGBColor of(RGBLike value) {
        return RGBColor.of(value.red(), value.green(), value.blue());
    }

    static RGBColor of(int red, int green, int blue) {
        return new RGBColorImpl((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
    }

    int value();

    @Override
    @Range(from = 0L, to = 255L)
    default int red() {
        return value() >> 16 & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int green() {
        return value() >> 8 & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int blue() {
        return value() & 0xFF;
    }

    default RGBColor withRed(int red) {
        return new RGBColorImpl(value() & 0x00FFFF | (red & 0xFF) << 16);
    }

    default RGBColor withGreen(int green) {
        return new RGBColorImpl(value() & 0xFF00FF | (green & 0xFF) << 8);
    }

    default RGBColor withBlue(int blue) {
        return new RGBColorImpl(value() & 0xFFFF00 | blue & 0xFF);
    }
}
