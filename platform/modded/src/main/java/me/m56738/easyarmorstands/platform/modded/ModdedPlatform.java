package me.m56738.easyarmorstands.platform.modded;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.entity.Pose;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.modded.block.ModdedBlockData;
import me.m56738.easyarmorstands.platform.modded.dialog.ModdedDialogFactory;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntityType;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPose;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedInventoryFactory;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemType;
import me.m56738.easyarmorstands.platform.modded.scheduler.ModdedScheduler;
import me.m56738.easyarmorstands.platform.modded.world.ModdedWorld;
import me.m56738.easyarmorstands.platform.scheduler.Scheduler;
import me.m56738.easyarmorstands.platform.util.MappedCollection;
import me.m56738.easyarmorstands.platform.util.MappedIterable;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.Mannequin;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public abstract class ModdedPlatform implements Platform {
    private @Nullable MinecraftServer server;
    private @Nullable MinecraftServerAudiences adventure;
    private final ComponentLogger logger;
    private final Scheduler scheduler;
    private final ModdedClickActionRegistry clickActionRegistry = new ModdedClickActionRegistry();
    private final ModdedDialogFactory dialogFactory = new ModdedDialogFactory(this);
    private final ModdedInventoryFactory inventoryFactory = new ModdedInventoryFactory();

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    protected ModdedPlatform(ComponentLogger logger) {
        this.logger = logger;
        this.scheduler = new ModdedScheduler();

        SCHEDULER.scheduleAtFixedRate(clickActionRegistry::clean, 0, 10, TimeUnit.SECONDS);
    }

    public void initialize(MinecraftServer server) {
        this.server = server;
        this.adventure = MinecraftServerAudiences.of(server);
    }

    private void throwNotInitialized() {
        throw new IllegalStateException("Not initialized");
    }

    public MinecraftServer getServer() {
        if (server == null) {
            throwNotInitialized();
        }
        return server;
    }

    public RegistryAccess getRegistryAccess() {
        return getServer().registryAccess();
    }

    public MinecraftServerAudiences getAdventure() {
        if (adventure == null) {
            throwNotInitialized();
        }
        return adventure;
    }

    public ComponentLogger getLogger() {
        return logger;
    }

    @Override
    public String getGameVersion() {
        return getServer().getServerVersion();
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public EntityType getEntityType(Key key) {
        return getRegistryAccess().get(ResourceKey.create(Registries.ENTITY_TYPE, MinecraftAudiences.asNative(key)))
                .map(r -> ModdedEntityType.fromNative(this, r.value()))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Iterable<EntityType> getEntityTypes() {
        return new MappedIterable<>(getRegistryAccess().lookupOrThrow(Registries.ENTITY_TYPE), t -> ModdedEntityType.fromNative(this, t));
    }

    @Override
    public ItemType getItemType(Key key) {
        return getRegistryAccess().get(ResourceKey.create(Registries.ITEM, MinecraftAudiences.asNative(key)))
                .map(r -> ModdedItemType.fromNative(this, r.value()))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public @Nullable World getWorld(Key key) {
        ServerLevel level = getServer().getLevel(ResourceKey.create(Registries.DIMENSION, MinecraftAudiences.asNative(key)));
        if (level == null) {
            return null;
        }
        return ModdedWorld.fromNative(this, level);
    }

    @Override
    public @Nullable Entity getEntity(UUID uniqueId) {
        for (ServerLevel level : getServer().getAllLevels()) {
            net.minecraft.world.entity.Entity entity = level.getEntity(uniqueId);
            if (entity != null) {
                return ModdedEntity.fromNative(this, entity);
            }
        }
        return null;
    }

    @Override
    public void checkMainThread() {
        if (!getServer().isSameThread()) {
            throw new IllegalStateException("Must be called on main thread");
        }
    }

    @Override
    public BlockData parseBlockData(String input) throws IllegalArgumentException {
        BlockStateParser.BlockResult result;
        try {
            result = BlockStateParser.parseForBlock(getRegistryAccess().lookupOrThrow(Registries.BLOCK), input, false);
        } catch (CommandSyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        return ModdedBlockData.fromNative(this, result.blockState());
    }

    @Override
    public Collection<Pose> getValidMannequinPoses() {
        return new MappedCollection<>(Mannequin.VALID_POSES, p -> ModdedPose.fromNative(this, p));
    }

    @Override
    public ModdedDialogFactory getDialogFactory() {
        return dialogFactory;
    }

    @Override
    public ModdedInventoryFactory getInventoryFactory() {
        return inventoryFactory;
    }

    public Identifier registerCustomClickAction(BiConsumer<DialogResponseView, Audience> action, ClickCallback.Options options) {
        return clickActionRegistry.registerClickAction(action, options);
    }

    public abstract boolean hasPermission(ServerPlayer player, String permission);

    public abstract boolean hasPermission(CommandSourceStack stack, String permission);
}
