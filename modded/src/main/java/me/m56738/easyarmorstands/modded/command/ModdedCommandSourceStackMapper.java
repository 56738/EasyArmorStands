package me.m56738.easyarmorstands.modded.command;

import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.command.ModdedCommandSender;
import net.minecraft.commands.CommandSourceStack;
import org.incendo.cloud.SenderMapper;

public class ModdedCommandSourceStackMapper implements SenderMapper<CommandSourceStack, EasCommandSender> {
    private final ModdedPlatform platform;
    private final CommandSenderMapper commandSenderMapper;

    public ModdedCommandSourceStackMapper(ModdedPlatform platform, CommandSenderMapper commandSenderMapper) {
        this.platform = platform;
        this.commandSenderMapper = commandSenderMapper;
    }

    @Override
    public EasCommandSender map(CommandSourceStack base) {
        EasCommandSender sender = commandSenderMapper.map(ModdedCommandSender.fromNative(platform, base));
        sender.setSource(base);
        return sender;
    }

    @Override
    public CommandSourceStack reverse(EasCommandSender mapped) {
        Object source = mapped.getSource();
        if (source instanceof CommandSourceStack) {
            return (CommandSourceStack) source;
        } else {
            throw new IllegalArgumentException("Unexpected command sender");
        }
    }
}
