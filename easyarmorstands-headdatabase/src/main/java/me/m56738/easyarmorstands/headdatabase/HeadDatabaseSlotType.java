package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class HeadDatabaseSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "headdatabase");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new HeadDatabaseSlotFactory(
                node.node("item").get(ItemTemplate.class));
    }
}
