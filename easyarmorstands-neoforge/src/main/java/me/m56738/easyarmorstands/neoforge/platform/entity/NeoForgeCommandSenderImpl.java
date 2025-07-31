package me.m56738.easyarmorstands.neoforge.platform.entity;

import me.m56738.easyarmorstands.modded.platform.entity.ModdedCommandSenderImpl;
import net.kyori.adventure.audience.Audience;
import net.minecraft.commands.CommandSourceStack;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NeoForgeCommandSenderImpl that = (NeoForgeCommandSenderImpl) o;
        return Objects.equals(stack, that.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stack);
    }
}
