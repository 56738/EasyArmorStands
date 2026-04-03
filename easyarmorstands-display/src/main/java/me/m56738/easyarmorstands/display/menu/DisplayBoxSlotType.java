package me.m56738.easyarmorstands.display.menu;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class DisplayBoxSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "display/box");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new DisplayBoxSlotFactory(
                node.node("item").get(SimpleItemTemplate.class));
    }
}
