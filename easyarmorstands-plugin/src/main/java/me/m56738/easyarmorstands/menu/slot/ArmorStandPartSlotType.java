package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPartSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "armor_stand/part");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new ArmorStandPartSlotFactory(
                node.node("part").get(ArmorStandPart.class),
                node.node("item").get(SimpleItemTemplate.class));
    }
}
