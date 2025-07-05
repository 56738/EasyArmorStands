package me.m56738.easyarmorstands.capability.command.v1_20_6_paper;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.SenderMapper;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CommandSourceStackMapper implements SenderMapper<CommandSourceStack, EasCommandSender> {
    private final CommandSenderMapper commandSenderMapper;

    public CommandSourceStackMapper(CommandSenderMapper commandSenderMapper) {
        this.commandSenderMapper = commandSenderMapper;
    }

    @Override
    public @NonNull EasCommandSender map(@NonNull CommandSourceStack base) {
        EasCommandSender sender = commandSenderMapper.map(base.getSender());
        sender.setSource(base);
        return sender;
    }

    @Override
    public @NonNull CommandSourceStack reverse(@NonNull EasCommandSender mapped) {
        Object source = mapped.getSource();
        if (source instanceof CommandSourceStack) {
            return (CommandSourceStack) source;
        } else {
            throw new IllegalArgumentException("Unexpected command sender");
        }
    }
}
