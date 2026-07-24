package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.fabric.api.EasyArmorStandsFabric;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.EasyArmorStandsModdedImpl;
import me.m56738.easyarmorstands.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.incendo.cloud.fabric.FabricServerCommandManager;

public class EasyArmorStandsFabricImpl extends EasyArmorStandsModdedImpl implements EasyArmorStandsFabric {
    public EasyArmorStandsFabricImpl(TranslationManager translationManager, Platform platform, FabricServerCommandManager<EasCommandSender> commandManager) {
        super(translationManager, platform, commandManager);
    }

    @Override
    public String getVersion() {
        return FabricLoader.getInstance().getModContainer("easyarmorstands")
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getVersion)
                .map(Version::getFriendlyString)
                .orElse("Unknown");
    }
}
