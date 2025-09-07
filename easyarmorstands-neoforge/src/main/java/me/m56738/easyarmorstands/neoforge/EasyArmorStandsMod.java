package me.m56738.easyarmorstands.neoforge;

import com.mojang.logging.LogUtils;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.common.permission.CommonPermissions;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.command.MainThreadExecutor;
import me.m56738.easyarmorstands.modded.platform.command.ModdedSenderMapper;
import me.m56738.easyarmorstands.neoforge.element.NeoForgeEntityElementListener;
import me.m56738.easyarmorstands.neoforge.permission.NeoForgePermissions;
import me.m56738.easyarmorstands.neoforge.platform.NeoForgePlatformImpl;
import me.m56738.easyarmorstands.neoforge.session.NeoForgeSessionListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.stream.Collectors;

@Mod(EasyArmorStandsMod.MODID)
public class EasyArmorStandsMod implements EasyArmorStandsCommonProvider {
    public static final String MODID = "easyarmorstands";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final ModContainer modContainer;
    private final MainThreadExecutor executor = new MainThreadExecutor();
    private final NeoForgePermissions permissions = new NeoForgePermissions();
    private @Nullable EasyArmorStandsCommon eas;

    public EasyArmorStandsMod(ModContainer modContainer, IEventBus modBus) {
        this.modContainer = modContainer;
        NeoForgeServerCommandManager<CommandSource> commandManager = new NeoForgeServerCommandManager<>(
                ExecutionCoordinator.coordinatorFor(executor),
                new ModdedSenderMapper(this));

        EasyArmorStandsCommon.registerCommands(commandManager, this);

        permissions.registerAll(CommonPermissions.createPermissions(
                BuiltInRegistries.ENTITY_TYPE.stream()
                        .map(EntityType::toShortString)
                        .collect(Collectors.toSet())));

        NeoForge.EVENT_BUS.addListener(this::onStarting);
        NeoForge.EVENT_BUS.addListener(this::onStopped);
        NeoForge.EVENT_BUS.addListener(this::onTick);
        NeoForge.EVENT_BUS.addListener(this::onGatherNodes);

        new NeoForgeSessionListener(this).register(modBus);
        new NeoForgeEntityElementListener(this).register();
    }

    private void onStarting(ServerStartingEvent event) {
        executor.setServer(event.getServer());
        String version = modContainer.getModInfo().getVersion().toString();
        ModdedPlatformImpl platform = new NeoForgePlatformImpl(event.getServer(), permissions);
        eas = new EasyArmorStandsCommon(platform, version);
    }

    private void onStopped(ServerStoppedEvent event) {
        if (eas != null) {
            eas.close();
            eas = null;
        }
        executor.setServer(null);
    }

    private void onTick(ServerTickEvent.Post event) {
        if (eas != null) {
            eas.getSessionListener().update();
            eas.getSessionManager().update();
        }
    }

    private void onGatherNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(permissions.getNodes().toArray(PermissionNode[]::new));
    }

    @Override
    public boolean hasEasyArmorStands() {
        return eas != null;
    }

    @Override
    public EasyArmorStandsCommon getEasyArmorStands() {
        return Objects.requireNonNull(eas);
    }
}
