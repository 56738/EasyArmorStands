package me.m56738.easyarmorstands.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.util.BoundingBox;

import java.util.Set;
import java.util.UUID;

public interface Entity {
    EntityType getType();

    UUID getUniqueId();

    int getEntityId();

    Location getLocation();

    void setLocation(Location location);

    double getWidth();

    double getHeight();

    default BoundingBox getBoundingBox() {
        return BoundingBox.of(getLocation().position(), getWidth(), getHeight());
    }

    boolean isValid();

    boolean isDead();

    Set<String> getTags();

    void addTag(String tag);

    void removeTag(String tag);

    void remove();
}
