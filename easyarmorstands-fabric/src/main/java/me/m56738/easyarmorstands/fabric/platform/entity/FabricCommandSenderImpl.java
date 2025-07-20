package me.m56738.easyarmorstands.fabric.platform.entity;

import me.lucko.fabric.api.permissions.v0.Permissions;
import me.m56738.easyarmorstands.modded.platform.entity.ModdedCommandSenderImpl;
import net.minecraft.commands.CommandSourceStack;

public class FabricCommandSenderImpl extends ModdedCommandSenderImpl {
    private final CommandSourceStack stack;

    public FabricCommandSenderImpl(CommandSourceStack stack) {
        super(stack);
        this.stack = stack;
    }

    @Override
    public boolean hasPermission(String permission) {
        return Permissions.check(stack, permission, stack.getServer().getOperatorUserPermissionLevel());
    }
}
