package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedCommandSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;

public abstract class ModdedCommandSenderImpl implements ModdedCommandSender, ForwardingAudience.Single {
    private final ModdedPlatform platform;
    private final Audience audience;

    public ModdedCommandSenderImpl(ModdedPlatform platform, Audience audience) {
        this.platform = platform;
        this.audience = audience;
    }

    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Audience audience() {
        return audience;
    }
}
