package me.m56738.easyarmorstands.paper.platform.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperCommandSender;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.bukkit.entity.Player;
import org.incendo.cloud.SenderMapper;

public class PaperSenderMapper implements SenderMapper<CommandSourceStack, PaperCommandSource> {
    private final PaperPlatform platform;

    public PaperSenderMapper(PaperPlatform platform) {
        this.platform = platform;
    }

    @Override
    public PaperCommandSource map(CommandSourceStack base) {
        if (base.getSender() instanceof Player player) {
            return new PaperPlayerCommandSource(base, PaperPlayer.fromNative(player));
        } else {
            return new PaperCommandSource(base, PaperCommandSender.fromNative(base.getSender()));
        }
    }

    @Override
    public CommandSourceStack reverse(PaperCommandSource mapped) {
        return mapped.stack();
    }
}
