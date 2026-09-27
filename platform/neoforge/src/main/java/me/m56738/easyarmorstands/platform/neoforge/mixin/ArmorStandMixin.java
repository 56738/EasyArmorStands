package me.m56738.easyarmorstands.platform.neoforge.mixin;

import me.m56738.easyarmorstands.platform.neoforge.event.ArmorStandBreakEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin extends LivingEntity {
    protected ArmorStandMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "kill", at = @At("HEAD"))
    private void kill(ServerLevel level, Entity attributedTo, CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new ArmorStandBreakEvent(this, attributedTo));
    }
}
