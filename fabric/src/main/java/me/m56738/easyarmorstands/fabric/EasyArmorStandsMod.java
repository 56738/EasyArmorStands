package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.fabric.event.FabricPlatformEvents;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.EasyArmorStandsModdedImpl;
import me.m56738.easyarmorstands.modded.command.ModdedArgumentParserProvider;
import me.m56738.easyarmorstands.modded.command.ModdedCommandSourceStackMapper;
import me.m56738.easyarmorstands.modded.util.MainThreadExecutor;
import me.m56738.easyarmorstands.platform.fabric.FabricPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.block.ModdedBlock;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.jspecify.annotations.Nullable;

public class EasyArmorStandsMod implements ModInitializer {
    private final MainThreadExecutor executor = new MainThreadExecutor(null);
    private final EasyArmorStandsHolder holder = new EasyArmorStandsHolder();

    @Override
    public void onInitialize() {
        FabricPlatform platform = new FabricPlatform(EasyArmorStandsModdedImpl.LOGGER);

        FabricServerCommandManager<EasCommandSender> commandManager = new FabricServerCommandManager<>(
                ExecutionCoordinator.coordinatorFor(executor),
                new ModdedCommandSourceStackMapper(platform, new CommandSenderMapper(holder)));

        ArgumentParserProvider parserProvider = new ModdedArgumentParserProvider(platform);
        EasyArmorStandsCommon.registerCommands(commandManager, parserProvider, getClass().getClassLoader(), holder);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            platform.initialize(server);
            executor.setServer(server);

            TranslationManager translationManager = new TranslationManager();
            translationManager.load(null, EasyArmorStandsModdedImpl.LOGGER);

            EasyArmorStandsFabricImpl eas = new EasyArmorStandsFabricImpl(translationManager, platform, commandManager);
            holder.initialize(eas);
            eas.onLoad();
            eas.onEnable();
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(_ -> {
            if (holder.isInitialized()) {
                holder.get().onDisable();
            }
            holder.initialize(null);
            executor.setServer(null);
        });

        ServerTickEvents.END_SERVER_TICK.register(_ -> {
            if (holder.isInitialized()) {
                holder.get().update();
            }
        });

        UseBlockCallback.EVENT.register((player, level, _, hitResult) ->
                result(handleClick(platform, player, level, ClickContext.Type.RIGHT_CLICK, null, hitResult.getBlockPos())));
        UseEntityCallback.EVENT.register((player, level, _, entity, _) ->
                result(handleClick(platform, player, level, ClickContext.Type.RIGHT_CLICK, entity, null)));
        ItemEvents.USE.register((level, player, _) ->
                resultOrNull(handleClick(platform, player, level, ClickContext.Type.RIGHT_CLICK, null, null)));
        AttackBlockCallback.EVENT.register((player, level, _, pos, _) ->
                result(handleClick(platform, player, level, ClickContext.Type.LEFT_CLICK, null, pos)));
        AttackEntityCallback.EVENT.register((player, level, _, entity, _) ->
                result(handleClick(platform, player, level, ClickContext.Type.LEFT_CLICK, entity, null)));
        FabricPlatformEvents.ARM_SWING.register(player ->
                handleClick(platform, player, player.level(), ClickContext.Type.LEFT_CLICK, null, null));
        FabricPlatformEvents.SELECT_SLOT.register((player, _) ->
                handleSwitchSlot(platform, player));
    }

    private InteractionResult result(boolean ok) {
        return ok ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    private @Nullable InteractionResult resultOrNull(boolean ok) {
        return ok ? InteractionResult.CONSUME : null;
    }

    private boolean handleClick(ModdedPlatform platform, Player player, Level level, ClickContext.Type type, @Nullable Entity entity, @Nullable BlockPos block) {
        if (!holder.isInitialized()) {
            return false;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        ModdedPlayer moddedPlayer = ModdedPlayer.fromNative(platform, serverPlayer);
        ModdedEntity moddedEntity = entity != null ? ModdedEntity.fromNative(platform, entity) : null;
        ModdedBlock moddedBlock = block != null ? ModdedBlock.fromNative(platform, serverLevel, block) : null;
        return holder.get().handleClick(moddedPlayer, type, moddedEntity, moddedBlock);
    }

    private void handleSwitchSlot(ModdedPlatform platform, Player player) {
        if (!holder.isInitialized()) {
            return;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        holder.get().handleSwitchSlot(ModdedPlayer.fromNative(platform, serverPlayer));
    }
}
