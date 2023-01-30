package me.m56738.easyarmorstands.bukkit.platform;

import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BukkitCommandSender extends BukkitWrapper<CommandSender> implements EasCommandSender, ForwardingAudience.Single {
    private final Audience audience;

    public BukkitCommandSender(BukkitPlatform platform, CommandSender sender, Audience audience) {
        super(platform, sender);
        this.audience = audience;
    }

    @Override
    public boolean hasPermission(String permission) {
        return get().hasPermission(permission);
    }

    @Override
    public @NotNull Audience audience() {
        return audience;
    }
}
