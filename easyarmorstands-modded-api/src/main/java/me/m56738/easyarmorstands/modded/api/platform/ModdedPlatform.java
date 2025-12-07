package me.m56738.easyarmorstands.modded.api.platform;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedCommandSender;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntityType;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import me.m56738.easyarmorstands.modded.api.platform.profile.ModdedProfile;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedBlock;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedBlockData;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ModdedPlatform extends Platform {
    MinecraftServer getServer();

    MinecraftServerAudiences getAdventure();

    ModdedProfile getProfile(ResolvableProfile nativeProfile);

    ModdedItem getItem(ItemStack nativeItem);

    ModdedPlayer getPlayer(ServerPlayer nativePlayer);

    ModdedEntity getEntity(Entity nativeEntity);

    ModdedEntityType getEntityType(EntityType<?> nativeEntityType);

    ModdedBlockData getBlockData(BlockState nativeBlockState);

    ModdedWorld getWorld(Level nativeLevel);

    ModdedBlock getBlock(Level nativeLevel, BlockPos nativeBlockPos);

    ModdedCommandSender getCommandSender(CommandSourceStack stack);
}
