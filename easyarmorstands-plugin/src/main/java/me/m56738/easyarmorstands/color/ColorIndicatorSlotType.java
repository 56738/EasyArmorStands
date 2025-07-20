package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.menu.slot.MenuSlotType;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ColorIndicatorSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "color_picker/indicator");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new ColorIndicatorSlotFactory();
    }
}
