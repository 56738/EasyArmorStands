package me.m56738.easyarmorstands.command.sender;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EasCommandSender implements ForwardingAudience.Single {
    private final @NotNull CommandSender sender;
    private final @NotNull Audience audience;

    public EasCommandSender(@NotNull CommandSender sender, @NotNull Audience audience) {
        this.sender = sender;
        this.audience = audience;
    }

    public @NotNull CommandSender get() {
        return sender;
    }

    @Override
    public @NotNull Audience audience() {
        return audience;
    }
}
