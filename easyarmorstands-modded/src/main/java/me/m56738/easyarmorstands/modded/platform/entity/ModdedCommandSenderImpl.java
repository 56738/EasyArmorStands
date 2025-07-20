package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedCommandSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;

public class ModdedCommandSenderImpl implements ModdedCommandSender, ForwardingAudience.Single {
    private final Audience audience;

    public ModdedCommandSenderImpl(Audience audience) {
        this.audience = audience;
    }

    @Override
    public Audience audience() {
        return audience;
    }
}
