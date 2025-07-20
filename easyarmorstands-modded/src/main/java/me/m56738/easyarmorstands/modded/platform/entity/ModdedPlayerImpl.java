package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minecraft.world.entity.player.Player;

public class ModdedPlayerImpl extends ModdedCommandSenderImpl implements ModdedPlayer, ForwardingAudience.Single {
    private final Player player;

    public ModdedPlayerImpl(Player player) {
        super((Audience) player);
        this.player = player;
    }

    @Override
    public Player getNative() {
        return player;
    }

    @Override
    public boolean isSneaking() {
        return player.isShiftKeyDown();
    }

    @Override
    public boolean isValid() {
        return player.isAlive();
    }
}
