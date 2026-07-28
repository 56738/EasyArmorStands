package me.m56738.easyarmorstands.platform.fabric.mixin;

import me.m56738.easyarmorstands.platform.fabric.FabricPlatformEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin extends LivingEntity {
    @Shadow
    private boolean invisible;

    protected ArmorStandMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Unique
    private @Nullable DamageSource currentDamageSource;

    @Inject(method = "hurtServer", at = @At("HEAD"))
    private void hurtServerHead(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        currentDamageSource = source;
    }

    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void hurtServerTail(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        currentDamageSource = null;
    }

    @Inject(method = "kill", at = @At("HEAD"))
    private void kill(ServerLevel level, CallbackInfo ci) {
        if (currentDamageSource == null) {
            return;
        }
        if (currentDamageSource.getEntity() instanceof Player player) {
            FabricPlatformEvents.ENTITY_KILL.invoker().onKillEntity(player, this);
        }
    }
}
