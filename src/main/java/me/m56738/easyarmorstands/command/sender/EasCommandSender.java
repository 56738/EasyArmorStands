package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.platform.command.CommandSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class EasCommandSender implements ForwardingAudience.Single {
    private final @NotNull CommandSender sender;
    private Object source;

    public EasCommandSender(@NotNull CommandSender sender) {
        this.sender = sender;
    }

    @Contract(pure = true)
    public @NotNull CommandSender get() {
        return sender;
    }

    @Override
    public @NotNull Audience audience() {
        return sender;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }
}
