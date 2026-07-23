package me.m56738.easyarmorstands.platform.paper.command;

import me.m56738.easyarmorstands.platform.command.CommandSender;
import me.m56738.easyarmorstands.platform.paper.dialog.PaperDialog;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.dialog.DialogLike;
import org.bukkit.entity.Player;

public interface PaperCommandSender extends CommandSender, ForwardingAudience.Single {
    static PaperCommandSender fromNative(org.bukkit.command.CommandSender sender) {
        if (sender instanceof Player player) {
            return PaperPlayer.fromNative(player);
        } else {
            return new PaperCommandSenderImpl(sender);
        }
    }

    org.bukkit.command.CommandSender getNative();

    static org.bukkit.command.CommandSender toNative(CommandSender sender) {
        return ((PaperCommandSender) sender).getNative();
    }

    @Override
    default Audience audience() {
        return getNative();
    }

    @Override
    default boolean hasPermission(String permission) {
        return getNative().hasPermission(permission);
    }

    @Override
    default boolean isPermissionSet(String permission) {
        return getNative().isPermissionSet(permission);
    }

    @Override
    default void showDialog(DialogLike dialog) {
        if (dialog instanceof PaperDialog paperDialog) {
            getNative().showDialog(paperDialog.getNative());
        } else {
            getNative().showDialog(dialog);
        }
    }
}
