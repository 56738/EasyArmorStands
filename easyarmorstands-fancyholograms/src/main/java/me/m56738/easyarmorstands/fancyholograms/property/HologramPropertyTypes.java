package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.data.HologramData;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.List;

import static net.kyori.adventure.key.Key.key;

@NullMarked
public final class HologramPropertyTypes {
    public static final PropertyType<HologramData> DATA = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "fancyholograms/data"))
            .name(Component.translatable("easyarmorstands.property.hologram.data.name"))
            .permission("easyarmorstands.property.hologram.data"));
    public static final PropertyType<List<String>> TEXT = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "fancyholograms/text"))
            .name(Component.translatable("easyarmorstands.property.text-display.text.name"))
            .permission("easyarmorstands.property.fancyholograms.text"));

    private HologramPropertyTypes() {
    }
}
