package me.m56738.easyarmorstands.command;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSenderWrapper {
    private final BukkitAudiences adventure;

    public CommandSenderWrapper(BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    public EasCommandSender wrap(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return new EasPlayer(player, adventure.player(player));
        }
        return new EasCommandSender(sender, adventure.sender(sender));
    }
}
