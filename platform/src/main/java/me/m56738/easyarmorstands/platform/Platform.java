package me.m56738.easyarmorstands.platform;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.dialog.DialogFactory;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.entity.Pose;
import me.m56738.easyarmorstands.platform.inventory.InventoryFactory;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.scheduler.Scheduler;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface Platform {
    String getName();

    String getVersion();

    String getGameVersion();

    Scheduler getScheduler();

    EntityType getEntityType(Key key);

    Iterable<EntityType> getEntityTypes();

    ItemType getItemType(Key key);

    @Nullable World getWorld(Key key);

    @Nullable Entity getEntity(UUID uniqueId);

    void checkMainThread();

    BlockData parseBlockData(String input) throws IllegalArgumentException;

    Collection<Pose> getValidMannequinPoses();

    DialogFactory getDialogFactory();

    InventoryFactory getInventoryFactory();
}
