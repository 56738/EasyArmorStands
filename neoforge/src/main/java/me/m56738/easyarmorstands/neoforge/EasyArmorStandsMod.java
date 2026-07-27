package me.m56738.easyarmorstands.neoforge;


import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.EasyArmorStandsModdedImpl;
import me.m56738.easyarmorstands.modded.command.ModdedArgumentParserProvider;
import me.m56738.easyarmorstands.modded.command.ModdedCommandSourceStackMapper;
import me.m56738.easyarmorstands.modded.util.MainThreadExecutor;
import me.m56738.easyarmorstands.neoforge.api.EasyArmorStandsNeoForgeHolder;
import me.m56738.easyarmorstands.neoforge.permission.NeoForgePermissionRegistrar;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.neoforge.NeoForgePlatform;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;

@Mod(EasyArmorStandsMod.MOD_ID)
public class EasyArmorStandsMod {
    public static final ComponentLogger LOGGER = ComponentLogger.logger("EasyArmorStands");
    public static final String MOD_ID = EasyArmorStands.NAMESPACE;
    private final MainThreadExecutor executor = new MainThreadExecutor(null);
    private final EasyArmorStandsHolder holder = new EasyArmorStandsHolder();
    private final NeoForgePlatform platform;
    private final NeoForgeServerCommandManager<EasCommandSender> commandManager;
    private final ModContainer modContainer;

    public EasyArmorStandsMod(ModContainer modContainer) {
        this.modContainer = modContainer;

        this.platform = new NeoForgePlatform(LOGGER);
        this.commandManager = new NeoForgeServerCommandManager<>(
                ExecutionCoordinator.coordinatorFor(executor),
                new ModdedCommandSourceStackMapper(platform, new CommandSenderMapper(holder)));

        ArgumentParserProvider parserProvider = new ModdedArgumentParserProvider(platform);
        EasyArmorStandsCommon.registerCommands(commandManager, parserProvider, getClass().getClassLoader(), holder);

        NeoForge.EVENT_BUS.addListener(ServerAboutToStartEvent.class, this::onServerAboutToStart);
        NeoForge.EVENT_BUS.addListener(PermissionGatherEvent.Nodes.class, this::onPermissionGatherNodes);
        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(ServerStoppingEvent.class, this::onServerStopping);
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, this::onServerTickPost);
    }

    private void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        platform.initialize(server);
        executor.setServer(server);

        TranslationManager translationManager = new TranslationManager();
        translationManager.load(null, EasyArmorStandsModdedImpl.LOGGER);

        EasyArmorStandsNeoForgeImpl eas = new EasyArmorStandsNeoForgeImpl(translationManager, platform, commandManager, modContainer);
        holder.initialize(eas);
        EasyArmorStandsNeoForgeHolder.setInstance(eas);
        eas.onLoad();
    }

    private void onPermissionGatherNodes(PermissionGatherEvent.Nodes nodes) {
        Permissions.registerAll(platform, holder.get().propertyTypeRegistry(), new NeoForgePermissionRegistrar(platform.getPermissionManager()));
        nodes.addNodes(platform.getPermissionManager().getAllNodes());
    }

    private void onServerStarting(ServerStartingEvent event) {
    }

    private void onServerStopping(ServerStoppingEvent event) {
        EasyArmorStandsNeoForgeImpl instance = (EasyArmorStandsNeoForgeImpl) EasyArmorStandsNeoForgeHolder.getInstance();
        if (instance != null) {
            instance.onDisable();
        }
        holder.initialize(null);
        executor.setServer(null);
    }

    private void onServerTickPost(ServerTickEvent.Post event) {
        if (holder.isInitialized()) {
            holder.get().update();
        }
    }
}
