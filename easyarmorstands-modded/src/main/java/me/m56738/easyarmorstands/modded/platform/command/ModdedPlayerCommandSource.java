package me.m56738.easyarmorstands.modded.platform.command;

import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import net.minecraft.commands.CommandSourceStack;

public class ModdedPlayerCommandSource extends ModdedCommandSource implements PlayerCommandSource {
    private final ModdedPlayer sender;

    public ModdedPlayerCommandSource(CommandSourceStack stack, ModdedPlayer sender) {
        super(stack, sender);
        this.sender = sender;
    }

    @Override
    public ModdedPlayer source() {
        return sender;
    }
}
