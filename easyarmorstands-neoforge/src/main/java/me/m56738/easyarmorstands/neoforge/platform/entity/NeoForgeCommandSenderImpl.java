package me.m56738.easyarmorstands.neoforge.platform.entity;

import me.m56738.easyarmorstands.modded.platform.entity.ModdedCommandSenderImpl;
import net.kyori.adventure.audience.Audience;
import net.minecraft.commands.CommandSourceStack;

public class NeoForgeCommandSenderImpl extends ModdedCommandSenderImpl {
    private final CommandSourceStack stack;

    public NeoForgeCommandSenderImpl(CommandSourceStack stack) {
        super((Audience) stack);
        this.stack = stack;
    }

    @Override
    public boolean hasPermission(String permission) {
        return stack.hasPermission(stack.getServer().getOperatorUserPermissionLevel());
    }
}
