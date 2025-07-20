package me.m56738.easyarmorstands.paper.platform.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;

public class PaperPlayerCommandSource extends PaperCommandSource implements PlayerCommandSource {
    private final PaperPlayer sender;

    public PaperPlayerCommandSource(CommandSourceStack stack, PaperPlayer sender) {
        super(stack, sender);
        this.sender = sender;
    }

    @Override
    public PaperPlayer source() {
        return sender;
    }
}
