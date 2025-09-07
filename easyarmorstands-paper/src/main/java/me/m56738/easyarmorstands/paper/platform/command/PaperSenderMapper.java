package me.m56738.easyarmorstands.paper.platform.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import org.bukkit.entity.Player;
import org.incendo.cloud.SenderMapper;

public class PaperSenderMapper implements SenderMapper<CommandSourceStack, CommandSource> {
    private final PaperPlatform platform;

    public PaperSenderMapper(PaperPlatform platform) {
        this.platform = platform;
    }

    @Override
    public CommandSource map(CommandSourceStack base) {
        if (base.getSender() instanceof Player player) {
            return new PaperPlayerCommandSource(base, platform.getPlayer(player));
        } else {
            return new PaperCommandSource(base, platform.getCommandSender(base.getSender()));
        }
    }

    @Override
    public CommandSourceStack reverse(CommandSource mapped) {
        return ((PaperCommandSource) mapped).stack();
    }
}
