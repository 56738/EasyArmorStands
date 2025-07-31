package me.m56738.easyarmorstands.modded.platform;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import me.m56738.easyarmorstands.modded.platform.inventory.ModdedItemImpl;
import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.modded.api.ModdedServerGizmos;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.joml.Vector3dc;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class ModdedPlatformImpl implements ModdedPlatform, CommonPlatform {
    private final MinecraftServer server;
    private final MinecraftServerAudiences adventure;
    private final ModdedServerGizmos gizmos;

    public ModdedPlatformImpl(MinecraftServer server) {
        this.server = server;
        this.adventure = MinecraftServerAudiences.of(server);
        this.gizmos = ModdedServerGizmos.create();
    }

    @Override
    public ModdedItem getItem(ItemStack nativeItem) {
        return new ModdedItemImpl(this, nativeItem);
    }

    @Override
    public Item createTool() {
        ItemStack item = new ItemStack(Items.BLAZE_ROD);
        CompoundTag tag = new CompoundTag();
        tag.putByte("easyarmorstands_tool", (byte) 1);
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return getItem(item);
    }

    @Override
    public boolean isTool(Item item) {
        ItemStack itemStack = ModdedItem.toNative(item);
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            return customData.contains("easyarmorstands_tool");
        }
        return false;
    }

    @Override
    public @Nullable Entity getEntity(UUID uniqueId, @Nullable Location expectedLocation) {
        if (expectedLocation != null) {
            Level level = ModdedWorld.toNative(expectedLocation.world());
            Vector3dc position = expectedLocation.position();
            // load chunk at the expected location
            level.getChunk(SectionPos.blockToSectionCoord(position.x()), SectionPos.blockToSectionCoord(position.z()));
            Entity entity = getEntity(uniqueId, level);
            if (entity != null) {
                return entity;
            }
        }
        for (ServerLevel level : server.getAllLevels()) {
            Entity entity = getEntity(uniqueId, level);
            if (entity != null) {
                return entity;
            }
        }
        return null;
    }

    private @Nullable Entity getEntity(UUID uniqueId, Level level) {
        net.minecraft.world.entity.Entity nativeEntity = level.getEntity(uniqueId);
        if (nativeEntity != null) {
            return ModdedEntity.fromNative(nativeEntity);
        }
        return null;
    }

    @Override
    public boolean isIgnored(Entity entity) {
        return false;
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location location, double deltaX, double deltaY, double deltaZ) {
        // TODO
        return List.of();
    }

    @Override
    public Collection<Entity> getTaggedEntities(World world, String tag) {
        // TODO
        return List.of();
    }

    @Override
    public Collection<Entity> getAllEntities(World world) {
        ServerLevel level = (ServerLevel) ModdedWorld.toNative(world);
        List<Entity> entities = new ArrayList<>();
        for (net.minecraft.world.entity.Entity nativeEntity : level.getAllEntities()) {
            entities.add(ModdedEntity.fromNative(nativeEntity));
        }
        return entities;
    }

    @Override
    public Collection<EntityType> getAllEntityTypes() {
        return List.of();
    }

    @Override
    public Entity spawnEntity(EntityType type, Location location, Consumer<Entity> config) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void openSpawnMenu(Player player) {
        // TODO
    }

    @Override
    public GizmoFactory getGizmoFactory(Player player) {
        return gizmos.player(ModdedPlayer.toNative(player));
    }

    @Override
    public void close() {
        adventure.close();
        gizmos.close();
    }

    public MinecraftServerAudiences getAdventure() {
        return adventure;
    }
}
