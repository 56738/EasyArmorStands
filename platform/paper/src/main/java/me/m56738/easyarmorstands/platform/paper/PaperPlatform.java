package me.m56738.easyarmorstands.platform.paper;

import io.papermc.paper.ServerBuildInfo;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.entity.Pose;
import me.m56738.easyarmorstands.platform.paper.block.PaperBlockData;
import me.m56738.easyarmorstands.platform.paper.dialog.PaperDialogFactory;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntityType;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperInventoryFactory;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemType;
import me.m56738.easyarmorstands.platform.paper.scheduler.PaperScheduler;
import me.m56738.easyarmorstands.platform.paper.world.PaperWorld;
import me.m56738.easyarmorstands.platform.util.MappedIterable;
import net.kyori.adventure.key.Key;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PaperPlatform implements Platform {
    private final Server server;
    private final PaperDialogFactory dialogFactory = new PaperDialogFactory();
    private final PaperInventoryFactory inventoryFactory;
    private @Nullable Plugin plugin;
    private @Nullable PaperScheduler scheduler;

    public PaperPlatform(Server server) {
        this.server = server;
        this.inventoryFactory = new PaperInventoryFactory(server);
    }

    public void initialize(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = new PaperScheduler(server, plugin);
    }

    @Override
    public String getName() {
        return server.getName();
    }

    @Override
    public String getVersion() {
        return server.getVersion();
    }

    @Override
    public String getGameVersion() {
        return ServerBuildInfo.buildInfo().minecraftVersionId();
    }

    @Override
    public PaperScheduler getScheduler() {
        if (scheduler == null) {
            throw new IllegalStateException();
        }
        return scheduler;
    }

    @Override
    public PaperEntityType getEntityType(Key key) {
        return PaperEntityType.fromNative(Registry.ENTITY_TYPE.getOrThrow(key));
    }

    @Override
    public Iterable<EntityType> getEntityTypes() {
        return new MappedIterable<>(Registry.ENTITY_TYPE, PaperEntityType::fromNative);
    }

    @Override
    public PaperItemType getItemType(Key key) {
        return PaperItemType.fromNative(Registry.ITEM.getOrThrow(key));
    }

    @Override
    public @Nullable PaperWorld getWorld(Key key) {
        World world = server.getWorld(key);
        if (world == null) {
            return null;
        }
        return PaperWorld.fromNative(world);
    }

    @Override
    public @Nullable PaperEntity getEntity(UUID uniqueId) {
        Entity entity = server.getEntity(uniqueId);
        if (entity == null) {
            return null;
        }
        return PaperEntity.fromNative(entity);
    }

    @Override
    public void checkMainThread() {
        if (!server.isPrimaryThread()) {
            throw new IllegalStateException("Must be called on main thread");
        }
    }

    @Override
    public BlockData parseBlockData(String input) throws IllegalArgumentException {
        return PaperBlockData.fromNative(server.createBlockData(input));
    }

    @Override
    public Collection<Pose> getValidMannequinPoses() {
        return List.of();
    }

    @Override
    public PaperDialogFactory getDialogFactory() {
        return dialogFactory;
    }

    @Override
    public PaperInventoryFactory getInventoryFactory() {
        return inventoryFactory;
    }
}
