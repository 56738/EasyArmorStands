package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ColorAxisChangeSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "color_picker/change");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new ColorAxisChangeSlotFactory(
                node.node("axis").get(ColorAxis.class),
                node.node("item").get(SimpleItemTemplate.class),
                node.node("change", "left").getInt(),
                node.node("change", "right").getInt(),
                node.node("change", "shift").getInt());
    }
}
