package me.m56738.easyarmorstands.platform.modded.command;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.commands.CommandSourceStack;

record ModdedCommandSourceStackImpl(
        ModdedPlatform platform, CommandSourceStack stack) implements ModdedCommandSourceStack {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public CommandSourceStack getNative() {
        return stack;
    }
}
