package me.m56738.easyarmorstands.command;

import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EasPlayer extends EasCommandSender {
    private final @NotNull Player player;

    public EasPlayer(@NotNull Player player, @NotNull Audience audience) {
        super(player, audience);
        this.player = player;
    }

    @Override
    public @NotNull Player get() {
        return player;
    }
}
