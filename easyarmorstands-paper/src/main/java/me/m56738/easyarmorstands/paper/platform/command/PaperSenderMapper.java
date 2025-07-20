package me.m56738.easyarmorstands.paper.platform.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.paper.platform.entity.PaperCommandSenderImpl;
import me.m56738.easyarmorstands.paper.platform.entity.PaperPlayerImpl;
import org.bukkit.entity.Player;
import org.incendo.cloud.SenderMapper;

public class PaperSenderMapper implements SenderMapper<CommandSourceStack, PaperCommandSource> {
    @Override
    public PaperCommandSource map(CommandSourceStack base) {
        if (base.getSender() instanceof Player player) {
            return new PaperPlayerCommandSource(base, new PaperPlayerImpl(player));
        } else {
            return new PaperCommandSource(base, new PaperCommandSenderImpl(base.getSender()));
        }
    }

    @Override
    public CommandSourceStack reverse(PaperCommandSource mapped) {
        return mapped.stack();
    }
}
