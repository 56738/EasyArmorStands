package me.m56738.easyarmorstands.api.platform;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface Platform {
    static Platform get() {
        return EasyArmorStands.get().platform();
    }

    String getEasyArmorStandsVersion();

    Item createTool();

    boolean isTool(Item item);

    @Nullable
    Entity getEntity(UUID uniqueId, @Nullable Location expectedLocation);

    Entity spawnEntity(EntityType type, Location location, Consumer<Entity> config);
}
