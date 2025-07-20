package me.m56738.easyarmorstands.modded.platform.command;

import me.m56738.easyarmorstands.modded.platform.entity.ModdedCommandSenderImpl;
import me.m56738.easyarmorstands.modded.platform.entity.ModdedPlayerImpl;
import net.kyori.adventure.audience.Audience;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import org.incendo.cloud.SenderMapper;

public class ModdedSenderMapper implements SenderMapper<CommandSourceStack, ModdedCommandSource> {
    @Override
    public ModdedCommandSource map(CommandSourceStack base) {
        if (base.getEntity() instanceof Player player) {
            return new ModdedPlayerCommandSource(base, new ModdedPlayerImpl(player));
        } else {
            return new ModdedCommandSource(base, new ModdedCommandSenderImpl((Audience) base));
        }
    }

    @Override
    public CommandSourceStack reverse(ModdedCommandSource mapped) {
        return mapped.stack();
    }
}
