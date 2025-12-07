package me.m56738.easyarmorstands.neoforge.platform.entity;

import me.m56738.easyarmorstands.modded.platform.entity.ModdedCommandSenderImpl;
import me.m56738.easyarmorstands.neoforge.api.platform.NeoForgePlatform;
import net.kyori.adventure.audience.Audience;
import net.minecraft.commands.CommandSourceStack;

import java.util.Objects;

public class NeoForgeCommandSenderImpl extends ModdedCommandSenderImpl {
    private final CommandSourceStack stack;

    public NeoForgeCommandSenderImpl(NeoForgePlatform platform, CommandSourceStack stack) {
        super(platform, (Audience) stack);
        this.stack = stack;
    }

    @Override
    public boolean hasPermission(String permission) {
        return stack.hasPermission(stack.getServer().operatorUserPermissionLevel());
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
