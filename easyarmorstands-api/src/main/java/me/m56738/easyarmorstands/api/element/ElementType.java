package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementType {
    @Nullable Element createElement(@NotNull PropertyContainer properties);

    default void applyDefaultProperties(@NotNull PropertyMap properties) {
    }

    @Contract(pure = true)
    @NotNull Component getDisplayName();

    @Contract(pure = true)
    boolean canSpawn(@NotNull Player player);
}
