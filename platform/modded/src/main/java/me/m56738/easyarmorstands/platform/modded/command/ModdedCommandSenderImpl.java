package me.m56738.easyarmorstands.platform.modded.command;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.commands.CommandSourceStack;

record ModdedCommandSenderImpl(ModdedPlatform platform, CommandSourceStack stack) implements ModdedCommandSender {
    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return false;
    }

    @Override
    public ModdedPlatform getPlatform() {
        return null;
    }
}
