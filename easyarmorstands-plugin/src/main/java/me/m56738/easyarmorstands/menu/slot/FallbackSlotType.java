package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class FallbackSlotType implements MenuSlotType {
    private final Key key;

    public FallbackSlotType(Key key) {
        this.key = key;
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new FallbackSlotFactory();
    }
}
