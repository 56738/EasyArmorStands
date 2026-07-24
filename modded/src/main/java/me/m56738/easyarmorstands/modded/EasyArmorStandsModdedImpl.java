package me.m56738.easyarmorstands.modded;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.api.EasyArmorStandsModded;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import me.m56738.gizmo.modded.api.ModdedServerGizmos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.incendo.cloud.CommandManager;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

public abstract class EasyArmorStandsModdedImpl extends EasyArmorStandsCommon implements EasyArmorStandsModded {
    private @Nullable ModdedServerGizmos gizmos;
    private @Nullable ParticleProviderFactory particleProviderFactory;

    public EasyArmorStandsModdedImpl(TranslationManager translationManager, Platform platform, CommandManager<EasCommandSender> commandManager) {
        super(translationManager, platform, commandManager);
    }

    @Override
    public ParticleProviderFactory particleProviderFactory() {
        if (particleProviderFactory == null) {
            throw new IllegalStateException();
        }
        return particleProviderFactory;
    }

    @Override
    public SessionToolProvider sessionToolProvider() {
        return null;
    }

    @Override
    public Path getConfigFolder() {
        return null;
    }

    @Override
    public ComponentLogger getLogger() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public EventDispatcher eventDispatcher() {
        return null;
    }

    @Override
    public Menu createColorPicker(Player player, Property<ItemStack> property) {
        return null;
    }

    @Override
    public boolean isColorPickerSupported(ItemStack item) {
        return false;
    }

    @Override
    public ItemStack createEntitySpawnEgg(Entity entity) {
        return null;
    }
}
