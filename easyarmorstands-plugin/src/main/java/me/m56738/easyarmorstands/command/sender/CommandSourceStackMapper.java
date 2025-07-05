package me.m56738.easyarmorstands.command.sender;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.lib.cloud.SenderMapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class CommandSourceStackMapper implements SenderMapper<CommandSourceStack, EasCommandSender> {
    @Override
    public @NonNull EasCommandSender map(@NonNull CommandSourceStack base) {
        CommandSender baseSender = base.getSender();
        EasCommandSender sender;
        if (baseSender instanceof Player player) {
            sender = new EasPlayer(player);
        } else {
            sender = new EasCommandSender(baseSender);
        }
        sender.setSource(base);
        return sender;
    }

    @Override
    public @NonNull CommandSourceStack reverse(@NonNull EasCommandSender mapped) {
        return Objects.requireNonNull(mapped.getSource());
    }
}
