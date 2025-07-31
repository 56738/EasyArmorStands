package me.m56738.easyarmorstands.fabric.mixin;

import me.m56738.easyarmorstands.fabric.platform.FabricPlatformEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void swingHand(InteractionHand hand, boolean updateSelf, CallbackInfo ci) {
        if ((Object) this instanceof Player player) {
            FabricPlatformEvents.ARM_SWING.invoker().onArmSwing(player);
        }
    }
}
