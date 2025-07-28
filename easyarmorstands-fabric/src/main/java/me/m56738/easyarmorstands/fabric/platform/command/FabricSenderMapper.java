package me.m56738.easyarmorstands.fabric.platform.command;

import me.m56738.easyarmorstands.fabric.platform.entity.FabricCommandSenderImpl;
import me.m56738.easyarmorstands.fabric.platform.entity.FabricPlayerImpl;
import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import me.m56738.easyarmorstands.modded.platform.command.ModdedPlayerCommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import org.incendo.cloud.SenderMapper;

public class FabricSenderMapper implements SenderMapper<CommandSourceStack, ModdedCommandSource> {
    @Override
    public ModdedCommandSource map(CommandSourceStack base) {
        if (base.getEntity() instanceof ServerPlayer player) {
            return new ModdedPlayerCommandSource(base, new FabricPlayerImpl(player));
        } else {
            return new ModdedCommandSource(base, new FabricCommandSenderImpl(base));
        }
    }

    @Override
    public CommandSourceStack reverse(ModdedCommandSource mapped) {
        return mapped.stack();
    }
}
