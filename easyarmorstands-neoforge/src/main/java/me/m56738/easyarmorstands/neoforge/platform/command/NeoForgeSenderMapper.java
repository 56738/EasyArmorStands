package me.m56738.easyarmorstands.neoforge.platform.command;

import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import me.m56738.easyarmorstands.modded.platform.command.ModdedPlayerCommandSource;
import me.m56738.easyarmorstands.neoforge.platform.entity.NeoForgeCommandSenderImpl;
import me.m56738.easyarmorstands.neoforge.platform.entity.NeoForgePlayerImpl;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import org.incendo.cloud.SenderMapper;

public class NeoForgeSenderMapper implements SenderMapper<CommandSourceStack, CommandSource> {
    @Override
    public CommandSource map(CommandSourceStack base) {
        if (base.getEntity() instanceof ServerPlayer player) {
            return new ModdedPlayerCommandSource(base, new NeoForgePlayerImpl(player));
        } else {
            return new ModdedCommandSource(base, new NeoForgeCommandSenderImpl(base));
        }
    }

    @Override
    public CommandSourceStack reverse(CommandSource mapped) {
        return ((ModdedCommandSource) mapped).stack();
    }
}
