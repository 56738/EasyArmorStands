package me.m56738.easyarmorstands.modded.platform.command;

import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedCommandSender;
import net.minecraft.commands.CommandSourceStack;

public class ModdedCommandSource implements CommandSource {
    private final CommandSourceStack stack;
    private final ModdedCommandSender sender;

    public ModdedCommandSource(CommandSourceStack stack, ModdedCommandSender sender) {
        this.stack = stack;
        this.sender = sender;
    }

    public CommandSourceStack stack() {
        return stack;
    }

    @Override
    public ModdedCommandSender source() {
        return sender;
    }
}
