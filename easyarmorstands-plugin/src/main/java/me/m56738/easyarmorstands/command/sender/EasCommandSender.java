package me.m56738.easyarmorstands.command.sender;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.util.TriState;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class EasCommandSender implements ForwardingAudience.Single {
    private final @NotNull CommandSender sender;
    private final @NotNull PermissionChecker permissions = new Permissions();
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

    public @NotNull PermissionChecker permissions() {
        return permissions;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    private class Permissions implements PermissionChecker {
        @Override
        public @NotNull TriState value(@NotNull String permission) {
            if (sender.isPermissionSet(permission)) {
                return TriState.byBoolean(sender.hasPermission(permission));
            }
            return TriState.NOT_SET;
        }

        @Override
        public boolean test(@NotNull String permission) {
            return sender.hasPermission(permission);
        }
    }
}
