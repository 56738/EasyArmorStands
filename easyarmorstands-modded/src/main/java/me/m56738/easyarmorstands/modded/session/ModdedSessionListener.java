package me.m56738.easyarmorstands.modded.session;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
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
            ModdedPlatform platform = (ModdedPlatform) eas.getPlatform();
            return eas.getSessionListener().handleClick(platform.getPlayer(serverPlayer), type,
                    entity != null ? platform.getEntity(entity) : null,
                    block != null ? platform.getBlock(level, block) : null);
        }
        return false;
    }

    public void handleUpdateItem(Player player) {
        if (easProvider.hasEasyArmorStands() && player instanceof ServerPlayer serverPlayer) {
            EasyArmorStandsCommon eas = easProvider.getEasyArmorStands();
            ModdedPlatform platform = (ModdedPlatform) eas.getPlatform();
            eas.getSessionListener().updateHeldItem(platform.getPlayer(serverPlayer));
        }
    }
}
