package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.lib.cloud.SenderMapper;
import me.m56738.easyarmorstands.lib.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CommandSenderMapper implements SenderMapper<CommandSender, EasCommandSender> {
    private final BukkitAudiences adventure;

    public CommandSenderMapper(BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    @Override
    public @NonNull EasCommandSender map(@NonNull CommandSender base) {
        if (base instanceof Player) {
            Player player = (Player) base;
            return new EasPlayer(player, adventure.player(player));
        }
        return new EasCommandSender(base, adventure.sender(base));
    }

    @Override
    public @NonNull CommandSender reverse(@NonNull EasCommandSender mapped) {
        return mapped.get();
    }
}
