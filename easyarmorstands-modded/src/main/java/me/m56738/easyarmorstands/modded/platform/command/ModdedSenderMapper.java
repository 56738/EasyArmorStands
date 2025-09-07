package me.m56738.easyarmorstands.modded.platform.command;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import org.incendo.cloud.SenderMapper;

public class ModdedSenderMapper implements SenderMapper<CommandSourceStack, CommandSource> {
    private final EasyArmorStandsCommonProvider easProvider;

    public ModdedSenderMapper(EasyArmorStandsCommonProvider easProvider) {
        this.easProvider = easProvider;
    }

    @Override
    public CommandSource map(CommandSourceStack base) {
        ModdedPlatform platform = (ModdedPlatform) easProvider.getEasyArmorStands().getPlatform();
        if (base.getEntity() instanceof ServerPlayer player) {
            return new ModdedPlayerCommandSource(base, platform.getPlayer(player));
        } else {
            return new ModdedCommandSource(base, platform.getCommandSender(base));
        }
    }

    @Override
    public CommandSourceStack reverse(CommandSource mapped) {
        return ((ModdedCommandSource) mapped).stack();
    }
}
