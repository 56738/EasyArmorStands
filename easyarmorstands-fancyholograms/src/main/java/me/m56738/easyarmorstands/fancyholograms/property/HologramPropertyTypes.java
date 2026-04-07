package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.data.HologramData;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class HologramPropertyTypes {
    public static final PropertyType<HologramData> DATA = PropertyType.builder(EasyArmorStands.key("fancyholograms/data"), HologramData.class)
            .name(Component.text("Data"))
            .formatter(value -> Component.text(value.getClass().getSimpleName()))
            .build();
    public static final PropertyType<List<String>> TEXT = PropertyType.<List<String>>builder(EasyArmorStands.key("fancyholograms/text"))
            .name(Component.translatable("easyarmorstands.property.text-display.text.name"))
            .formatter(value -> Component.text(String.join("\n", value)))
            .permission("easyarmorstands.property.fancyholograms.text")
            .build();

    private HologramPropertyTypes() {
    }
}
