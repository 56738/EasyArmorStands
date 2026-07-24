package me.m56738.easyarmorstands.modded;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.api.EasyArmorStandsModded;
import me.m56738.easyarmorstands.modded.particle.ModdedParticleProviderFactory;
import me.m56738.easyarmorstands.modded.session.ModdedSessionToolProvider;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import me.m56738.gizmo.modded.api.ModdedServerGizmos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.incendo.cloud.CommandManager;

public abstract class EasyArmorStandsModdedImpl extends EasyArmorStandsCommon implements EasyArmorStandsModded {
    public static final ComponentLogger LOGGER = ComponentLogger.logger("EasyArmorStands");

    private final ClassLoader classLoader;
    private final ModdedServerGizmos gizmos;
    private final ParticleProviderFactory particleProviderFactory;
    private final ModdedSessionToolProvider sessionToolProvider;

    public EasyArmorStandsModdedImpl(TranslationManager translationManager, ModdedPlatform platform, CommandManager<EasCommandSender> commandManager, ClassLoader classLoader) {
        super(translationManager, platform, commandManager);
        this.classLoader = classLoader;
        this.gizmos = ModdedServerGizmos.create();
        this.particleProviderFactory = new ModdedParticleProviderFactory(gizmos);
        this.sessionToolProvider = new ModdedSessionToolProvider(this);
    }

    @Override
    public ParticleProviderFactory particleProviderFactory() {
        return particleProviderFactory;
    }

    @Override
    public SessionToolProvider sessionToolProvider() {
        return sessionToolProvider;
    }

    @Override
    public ComponentLogger getLogger() {
        return LOGGER;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Menu createColorPicker(Player player, Property<ItemStack> property) {
        throw new IllegalArgumentException(); // TODO
    }

    @Override
    public boolean isColorPickerSupported(ItemStack item) {
        return false;
    }

    @Override
    public ItemStack createEntitySpawnEgg(Entity entity) {
        return ModdedItemStack.fromNative((ModdedPlatform) platform(), net.minecraft.world.item.ItemStack.EMPTY);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gizmos.close();
    }
}
