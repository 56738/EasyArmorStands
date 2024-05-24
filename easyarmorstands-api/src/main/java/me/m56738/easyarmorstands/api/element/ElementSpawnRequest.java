package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementSpawnRequest {
    @Contract(pure = true)
    @NotNull
    ElementType getType();

    @Contract(pure = true)
    @Nullable
    Player getPlayer();

    void setPlayer(@Nullable Player player);

    @Contract(pure = true)
    @NotNull
    PropertyMap getProperties();

    @Nullable
    Element spawn();
}
