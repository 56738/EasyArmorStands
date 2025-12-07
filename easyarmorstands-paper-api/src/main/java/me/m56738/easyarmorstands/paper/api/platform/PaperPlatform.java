package me.m56738.easyarmorstands.paper.api.platform;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperCommandSender;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import me.m56738.easyarmorstands.paper.api.platform.profile.PaperProfile;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlock;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperWorld;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

public interface PaperPlatform extends Platform {
    PaperEntity getEntity(Entity nativeEntity);

    PaperPlayer getPlayer(Player nativePlayer);

    PaperCommandSender getCommandSender(CommandSender nativeCommandSender);

    PaperWorld getWorld(World nativeWorld);

    PaperItem getItem(@Nullable ItemStack nativeItem);

    PaperEntityType getEntityType(EntityType nativeType);

    PaperBlock getBlock(Block nativeBlock);

    PaperBlockData getBlockData(BlockData nativeBlockData);

    PaperProfile getProfile(ResolvableProfile profile);
}
