package me.m56738.easyarmorstands.display.menu;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.display.element.InteractionElementType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.menu.slot.SpawnSlotFactory;
import org.jetbrains.annotations.NotNull;

public class InteractionSpawnSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "spawn/interaction");

    private final InteractionElementType type;

    public InteractionSpawnSlotType(InteractionElementType type) {
        this.type = type;
    }

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new SpawnSlotFactory(type, node.node("item").get(SimpleItemTemplate.class));
    }
}
