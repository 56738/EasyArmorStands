package me.m56738.easyarmorstands.modded.api.platform;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedCommandSender;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface ModdedPlatform extends Platform {
    ModdedItem getItem(ItemStack nativeItem);

    ModdedPlayer getPlayer(ServerPlayer nativePlayer);

    ModdedCommandSender getCommandSender(CommandSourceStack stack);
}
