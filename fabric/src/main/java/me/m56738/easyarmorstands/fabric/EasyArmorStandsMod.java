package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.fabric.api.EasyArmorStandsFabricHolder;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.EasyArmorStandsModdedImpl;
import me.m56738.easyarmorstands.modded.command.ModdedArgumentParserProvider;
import me.m56738.easyarmorstands.modded.command.ModdedCommandSourceStackMapper;
import me.m56738.easyarmorstands.modded.util.MainThreadExecutor;
import me.m56738.easyarmorstands.platform.fabric.FabricPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.item.ItemStack;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;

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

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            platform.initialize(server);
            executor.setServer(server);

            TranslationManager translationManager = new TranslationManager();
            translationManager.load(null, EasyArmorStandsModdedImpl.LOGGER);

            EasyArmorStandsFabricImpl eas = new EasyArmorStandsFabricImpl(translationManager, platform, commandManager);
            holder.initialize(eas);
            EasyArmorStandsFabricHolder.setInstance(eas);
            eas.onLoad();
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(_ -> {
            EasyArmorStandsFabricImpl instance = (EasyArmorStandsFabricImpl) EasyArmorStandsFabricHolder.getInstance();
            if (instance != null) {
                instance.onDisable();
            }
            EasyArmorStandsFabricHolder.setInstance(null);
            holder.initialize(null);
            executor.setServer(null);
        });

        ServerTickEvents.END_SERVER_TICK.register(_ -> {
            if (holder.isInitialized()) {
                holder.get().update();
            }
        });
    }

    public static boolean isTool(ItemStack item) {
        EasyArmorStandsModdedImpl eas = (EasyArmorStandsModdedImpl) EasyArmorStandsFabricHolder.getInstance();
        if (eas == null) {
            return false;
        }
        return eas.isTool(item);
    }
}
