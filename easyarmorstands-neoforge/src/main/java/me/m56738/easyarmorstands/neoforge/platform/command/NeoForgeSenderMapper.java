package me.m56738.easyarmorstands.neoforge.platform.command;

import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import me.m56738.easyarmorstands.modded.platform.command.ModdedPlayerCommandSource;
import me.m56738.easyarmorstands.neoforge.platform.entity.NeoForgeCommandSenderImpl;
import me.m56738.easyarmorstands.neoforge.platform.entity.NeoForgePlayerImpl;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import org.incendo.cloud.SenderMapper;

public class NeoForgeSenderMapper implements SenderMapper<CommandSourceStack, ModdedCommandSource> {
    @Override
    public ModdedCommandSource map(CommandSourceStack base) {
        if (base.getEntity() instanceof Player player) {
            return new ModdedPlayerCommandSource(base, new NeoForgePlayerImpl(player));
        } else {
            return new ModdedCommandSource(base, new NeoForgeCommandSenderImpl(base));
        }
    }

    @Override
    public CommandSourceStack reverse(ModdedCommandSource mapped) {
        return mapped.stack();
    }
}
