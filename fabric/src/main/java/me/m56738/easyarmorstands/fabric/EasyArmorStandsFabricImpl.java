package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.fabric.api.EasyArmorStandsFabric;
import me.m56738.easyarmorstands.fabric.event.FabricEventDispatcher;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.EasyArmorStandsModdedImpl;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.incendo.cloud.fabric.FabricServerCommandManager;

import java.nio.file.Path;

public class EasyArmorStandsFabricImpl extends EasyArmorStandsModdedImpl implements EasyArmorStandsFabric {
    private final FabricEventDispatcher eventDispatcher = new FabricEventDispatcher();

    public EasyArmorStandsFabricImpl(TranslationManager translationManager, ModdedPlatform platform, FabricServerCommandManager<EasCommandSender> commandManager) {
        super(translationManager, platform, commandManager, EasyArmorStandsFabricImpl.class.getClassLoader());
    }

    @Override
    public String getVersion() {
        return FabricLoader.getInstance().getModContainer("easyarmorstands")
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getVersion)
                .map(Version::getFriendlyString)
                .orElse("Unknown");
    }

    @Override
    public Path getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir().resolve("easyarmorstands");
    }

    @Override
    public EventDispatcher eventDispatcher() {
        return eventDispatcher;
    }
}
