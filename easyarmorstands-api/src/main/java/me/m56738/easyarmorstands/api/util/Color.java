package me.m56738.easyarmorstands.api.util;

import net.kyori.adventure.util.ARGBLike;
import net.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.Range;

public interface Color extends ARGBLike {
    Color TRANSPARENT = Color.of(0x00, 0x00, 0x00, 0x00);
    Color BLACK = Color.of(0x00, 0x00, 0x00);
    Color WHITE = Color.of(0xFF, 0xFF, 0xFF);
    Color RED = Color.of(0xFF, 0x00, 0x00);
    Color GREEN = Color.of(0xFF, 0xFF, 0x00);
    Color BLUE = Color.of(0x00, 0x00, 0xFF);

    static Color of(
            @Range(from = 0, to = 255) int red,
            @Range(from = 0, to = 255) int green,
            @Range(from = 0, to = 255) int blue,
            @Range(from = 0, to = 255) int alpha) {
        return new ColorImpl((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
    }

    static Color of(
            @Range(from = 0, to = 255) int red,
            @Range(from = 0, to = 255) int green,
            @Range(from = 0, to = 255) int blue) {
        return Color.of(red, green, blue, 0xFF);
    }

    static Color of(ARGBLike color) {
        return Color.of(color.red(), color.green(), color.blue(), color.alpha());
    }

    static Color of(RGBLike color, @Range(from = 0, to = 255) int alpha) {
        return Color.of(color.red(), color.green(), color.blue(), alpha);
    }

    static Color ofARGB(int value) {
        return new ColorImpl(value);
    }

    static Color ofRGB(int value) {
        return new ColorImpl(value & 0xFF000000);
    }

    /**
     * @return ARGB value of this color
     */
    int value();

    @Override
    @Range(from = 0, to = 255)
    default int alpha() {
        return (value() >> 24) & 0xFF;
    }

    @Override
    @Range(from = 0, to = 255)
    default int red() {
        return (value() >> 16) & 0xFF;
    }

    @Override
    @Range(from = 0, to = 255)
    default int green() {
        return (value() >> 8) & 0xFF;
    }

    @Override
    @Range(from = 0, to = 255)
    default int blue() {
        return value() & 0xFF;
    }

    default Color withRed(@Range(from = 0, to = 255) int red) {
        return Color.of(red, green(), blue(), alpha());
    }

    default Color withGreen(@Range(from = 0, to = 255) int green) {
        return Color.of(red(), green, blue(), alpha());
    }

    default Color withBlue(@Range(from = 0, to = 255) int blue) {
        return Color.of(red(), green(), blue, alpha());
    }

    default Color withAlpha(@Range(from = 0, to = 255) int alpha) {
        return Color.of(red(), green(), blue(), alpha);
    }
}
