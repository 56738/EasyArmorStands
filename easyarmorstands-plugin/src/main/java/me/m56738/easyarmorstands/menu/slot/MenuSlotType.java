package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface MenuSlotType extends Keyed {
    @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException;
}
