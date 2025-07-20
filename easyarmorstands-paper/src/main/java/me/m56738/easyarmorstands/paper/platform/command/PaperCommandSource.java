package me.m56738.easyarmorstands.paper.platform.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperCommandSender;

public class PaperCommandSource implements CommandSource {
    private final CommandSourceStack stack;
    private final PaperCommandSender sender;

    public PaperCommandSource(CommandSourceStack stack, PaperCommandSender sender) {
        this.stack = stack;
        this.sender = sender;
    }

    public CommandSourceStack stack() {
        return stack;
    }

    @Override
    public PaperCommandSender source() {
        return sender;
    }
}
