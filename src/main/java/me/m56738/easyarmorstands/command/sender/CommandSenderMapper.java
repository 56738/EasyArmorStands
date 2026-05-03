package me.m56738.easyarmorstands.command.sender;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

public class CommandSenderMapper implements SenderMapper<CommandSender, EasCommandSender> {
    @Override
    public @NonNull EasCommandSender map(@NonNull CommandSender base) {
        if (base instanceof Player) {
            Player player = (Player) base;
            return new EasPlayer(player);
        }
        return new EasCommandSender(base);
    }

    @Override
    public @NonNull CommandSender reverse(@NonNull EasCommandSender mapped) {
        return mapped.get();
    }
}
