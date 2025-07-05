package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.lib.cloud.SenderMapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CommandSenderMapper implements SenderMapper<CommandSender, EasCommandSender> {
    @Override
    public @NonNull EasCommandSender map(@NonNull CommandSender base) {
        if (base instanceof Player player) {
            return new EasPlayer(player);
        }
        return new EasCommandSender(base);
    }

    @Override
    public @NonNull CommandSender reverse(@NonNull EasCommandSender mapped) {
        return mapped.get();
    }
}
