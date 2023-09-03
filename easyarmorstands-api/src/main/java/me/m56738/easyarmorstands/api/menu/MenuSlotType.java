package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public interface MenuSlotType extends Keyed {
    @NotNull MenuSlotFactory load(ConfigurationNode node) throws SerializationException;
}
