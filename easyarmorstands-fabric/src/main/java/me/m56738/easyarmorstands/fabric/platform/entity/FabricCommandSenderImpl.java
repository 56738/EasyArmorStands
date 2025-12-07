package me.m56738.easyarmorstands.fabric.platform.entity;

import me.lucko.fabric.api.permissions.v0.Permissions;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.platform.entity.ModdedCommandSenderImpl;
import net.minecraft.commands.CommandSourceStack;

import java.util.Objects;

public class FabricCommandSenderImpl extends ModdedCommandSenderImpl {
    private final CommandSourceStack stack;

    public FabricCommandSenderImpl(ModdedPlatform platform, CommandSourceStack stack) {
        super(platform, stack);
        this.stack = stack;
    }

    @Override
    public boolean hasPermission(String permission) {
        return Permissions.check(stack, permission, stack.getServer().operatorUserPermissionLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FabricCommandSenderImpl that = (FabricCommandSenderImpl) o;
        return Objects.equals(stack, that.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stack);
    }
}
