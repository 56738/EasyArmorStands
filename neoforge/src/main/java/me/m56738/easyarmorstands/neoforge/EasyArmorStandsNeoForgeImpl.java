package me.m56738.easyarmorstands.neoforge;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.EasyArmorStandsModdedImpl;
import me.m56738.easyarmorstands.neoforge.api.EasyArmorStandsNeoForge;
import me.m56738.easyarmorstands.neoforge.event.NeoForgeEventDispatcher;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.gizmo.neoforge.api.NeoForgeServerGizmos;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLPaths;
import org.incendo.cloud.CommandManager;

import java.nio.file.Path;

public class EasyArmorStandsNeoForgeImpl extends EasyArmorStandsModdedImpl implements EasyArmorStandsNeoForge {
    private final ModContainer modContainer;
    private final NeoForgeEventDispatcher eventDispatcher = new NeoForgeEventDispatcher();

    public EasyArmorStandsNeoForgeImpl(TranslationManager translationManager, ModdedPlatform platform, CommandManager<EasCommandSender> commandManager, ModContainer modContainer) {
        super(translationManager, platform, commandManager, NeoForgeServerGizmos.create(), EasyArmorStandsNeoForgeImpl.class.getClassLoader());
        this.modContainer = modContainer;
    }

    @Override
    public String getVersion() {
        return modContainer.getModInfo().getVersion().toString();
    }

    @Override
    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get().resolve(EasyArmorStandsMod.MOD_ID);
    }

    @Override
    public EventDispatcher eventDispatcher() {
        return eventDispatcher;
    }

    @Override
    public boolean isIgnored(Entity entity) {
        return false;
    }
}
