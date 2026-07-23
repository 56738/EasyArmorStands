package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface Entity {
    World world();

    Location location();

    EntityType type();

    Entity copy(Location location);

    int id();

    UUID uniqueId();

    boolean isValid();

    double width();

    double height();

    void remove();

    boolean isGlowing();

    void setGlowing(boolean glowing);

    boolean teleport(Location location);

    void setFallDistance(float distance);

    Set<String> getScoreboardTags();

    boolean addScoreboardTag(String tag);

    boolean removeScoreboardTag(String tag);

    boolean hasMetadata(String key);

    @Nullable EntitySnapshot createSnapshot();

    ItemStack getPickItemStack();

    @Nullable String getCustomDataString(Key key);

    void setCustomDataString(Key key, String value);

    void removeCustomData(Key key);

    boolean isSilent();

    void setSilent(boolean silent);

    @Nullable Component getCustomName();

    void setCustomName(@Nullable Component customName);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean visible);
}
