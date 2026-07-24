package me.m56738.easyarmorstands.platform.modded.command;

import me.m56738.easyarmorstands.platform.command.CommandSender;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public interface ModdedCommandSender extends CommandSender, ModdedPlatformHolder {
    static ModdedCommandSender fromNative(ModdedPlatform platform, CommandSourceStack stack) {
        ServerPlayer player = stack.getPlayer();
        if (player != null) {
            return ModdedPlayer.fromNative(platform, player);
        } else {
            return new ModdedCommandSourceStackImpl(platform, stack);
        }
    }
}
