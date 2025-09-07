package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public abstract class ModdedPlayerImpl extends ModdedCommandSenderImpl implements ModdedPlayer, ForwardingAudience.Single {
    private final ServerPlayer player;

    public ModdedPlayerImpl(ModdedPlatformImpl platform, ServerPlayer player) {
        super(platform, platform.getAdventure().audience(player));
        this.player = player;
    }

    @Override
    public ServerPlayer getNative() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ModdedPlayerImpl that = (ModdedPlayerImpl) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(player);
    }
}
