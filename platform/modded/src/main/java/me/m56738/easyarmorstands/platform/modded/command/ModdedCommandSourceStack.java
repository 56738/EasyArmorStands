package me.m56738.easyarmorstands.platform.modded.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minecraft.commands.CommandSourceStack;

public interface ModdedCommandSourceStack extends ModdedCommandSender, ForwardingAudience.Single {
    CommandSourceStack getNative();

    @Override
    default Audience audience() {
        return getPlatform().getAdventure().audience(getNative());
    }

    @Override
    default boolean hasPermission(String permission) {
        return getPlatform().hasPermission(getNative(), permission);
    }
}
