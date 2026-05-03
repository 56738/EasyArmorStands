package me.m56738.easyarmorstands.api.region;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface RegionPrivilegeChecker {
    boolean isAllowed(Player player, Location location);

    default boolean isAllowed(Player player, Location location, boolean silent) {
        return isAllowed(player, location);
    }

    boolean canBypass(Player player);

    void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties);

    void sendDestroyError(@NotNull Player player, @NotNull Element element);

    void sendEditError(@NotNull Player player, @NotNull Element element);
}
