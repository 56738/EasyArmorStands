package me.m56738.easyarmorstands.modded.session;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public abstract class ModdedSessionListener {
    private final EasyArmorStandsCommonProvider easProvider;

    public ModdedSessionListener(EasyArmorStandsCommonProvider easProvider) {
        this.easProvider = easProvider;
    }

    public abstract void register();

    public boolean handleClick(Player player, Level level, ClickContext.Type type, @Nullable Entity entity, @Nullable BlockPos block) {
        if (easProvider.hasEasyArmorStands() && level instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
            EasyArmorStandsCommon eas = easProvider.getEasyArmorStands();
            ModdedPlatform platform = (ModdedPlatform) eas.platform();
            return eas.sessionListener().handleClick(platform.getPlayer(serverPlayer), type,
                    entity != null ? ModdedEntity.fromNative(entity) : null,
                    block != null ? ModdedWorld.fromNative(level).getBlock(block) : null);
        }
        return false;
    }

    public void handleUpdateItem(Player player) {
        if (easProvider.hasEasyArmorStands() && player instanceof ServerPlayer serverPlayer) {
            EasyArmorStandsCommon eas = easProvider.getEasyArmorStands();
            ModdedPlatform platform = (ModdedPlatform) eas.platform();
            eas.sessionListener().updateHeldItem(platform.getPlayer(serverPlayer));
        }
    }
}
