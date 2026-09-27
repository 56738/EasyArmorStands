package me.m56738.easyarmorstands.platform.fabric.mixin;

import me.m56738.easyarmorstands.platform.fabric.FabricPlatformEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "swing", at = @At("HEAD"))
    private void swingHand(InteractionHand hand, SwingAnimation animation, boolean sendToSwingingEntity, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            FabricPlatformEvents.ARM_SWING.invoker().onArmSwing(player);
        }
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void die(DamageSource source, CallbackInfo ci) {
        if (source.getEntity() instanceof Player player) {
            FabricPlatformEvents.ENTITY_KILL.invoker().onKillEntity(player, this);
        }
    }
}
