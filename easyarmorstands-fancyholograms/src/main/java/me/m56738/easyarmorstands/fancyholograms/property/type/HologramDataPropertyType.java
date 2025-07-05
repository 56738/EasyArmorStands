package me.m56738.easyarmorstands.fancyholograms.property.type;

import de.oliver.fancyholograms.api.data.HologramData;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HologramDataPropertyType implements PropertyType<HologramData> {
    public static final Key KEY = Key.key("easyarmorstands", "fancyholograms/data");
    public static final HologramDataPropertyType INSTANCE = new HologramDataPropertyType();

    @Override
    public @NotNull TypeToken<HologramData> getValueType() {
        return TypeToken.get(HologramData.class);
    }

    @Override
    public @Nullable String getPermission() {
        return null;
    }

    @Override
    public @NotNull Component getName() {
        return Component.text("Data");
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull HologramData value) {
        return Component.text(value.getClass().getSimpleName());
    }

    @Override
    public @NotNull Key key() {
        return KEY;
    }
}
