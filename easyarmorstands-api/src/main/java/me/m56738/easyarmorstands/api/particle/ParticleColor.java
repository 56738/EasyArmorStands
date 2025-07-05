package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.util.HSVLike;
import me.m56738.easyarmorstands.lib.kyori.adventure.util.RGBLike;
import org.jetbrains.annotations.NotNull;

public enum ParticleColor implements RGBLike {
    WHITE(NamedTextColor.WHITE),
    RED(NamedTextColor.RED),
    GREEN(NamedTextColor.GREEN),
    BLUE(NamedTextColor.BLUE),
    YELLOW(NamedTextColor.YELLOW),
    GRAY(NamedTextColor.GRAY),
    AQUA(NamedTextColor.AQUA);

    private final RGBLike color;

    ParticleColor(RGBLike color) {
        this.color = color;
    }

    @Override
    public int red() {
        return color.red();
    }

    @Override
    public int green() {
        return color.green();
    }

    @Override
    public int blue() {
        return color.blue();
    }

    @Override
    public @NotNull HSVLike asHSV() {
        return color.asHSV();
    }
}
