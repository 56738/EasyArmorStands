package me.m56738.easyarmorstands.modded;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.color.ColorPicker;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.modded.api.EasyArmorStandsModded;
import me.m56738.easyarmorstands.modded.color.ModdedColorPickerContext;
import me.m56738.easyarmorstands.modded.particle.ModdedParticleProviderFactory;
import me.m56738.easyarmorstands.modded.session.ModdedSessionToolProvider;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntitySnapshot;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import me.m56738.gizmo.modded.api.ModdedServerGizmos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.storage.TagValueOutput;
import org.incendo.cloud.CommandManager;

public abstract class EasyArmorStandsModdedImpl extends EasyArmorStandsCommon implements EasyArmorStandsModded {
    public static final ComponentLogger LOGGER = ComponentLogger.logger("EasyArmorStands");

    private final ModdedPlatform platform;
    private final ClassLoader classLoader;
    private final ModdedServerGizmos gizmos;
    private final ParticleProviderFactory particleProviderFactory;
    private final ModdedSessionToolProvider sessionToolProvider;

    public EasyArmorStandsModdedImpl(TranslationManager translationManager, ModdedPlatform platform, CommandManager<EasCommandSender> commandManager, ModdedServerGizmos gizmos, ClassLoader classLoader) {
        super(translationManager, platform, commandManager);
        this.platform = platform;
        this.classLoader = classLoader;
        this.gizmos = gizmos;
        this.particleProviderFactory = new ModdedParticleProviderFactory(gizmos);
        this.sessionToolProvider = new ModdedSessionToolProvider(this);
    }

    @Override
    public void update() {
        platform.update();
        super.update();
    }

    @Override
    public ModdedPlatform platform() {
        return platform;
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
        return ColorPicker.create(this, player, new ModdedColorPickerContext(platform, property));
    }

    @Override
    public boolean isColorPickerSupported(ItemStack item) {
        return ModdedColorPickerContext.isSupported(item);
    }

    @Override
    public ItemStack createEntitySpawnEgg(Entity entity) {
        net.minecraft.world.entity.Entity nativeEntity = ModdedEntity.toNative(entity);
        net.minecraft.world.item.ItemStack item = nativeEntity.getPickResult();
        if (item == null) {
            return ModdedItemStack.empty(platform);
        }
        item = item.copy();
        CompoundTag tag;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(nativeEntity.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, nativeEntity.registryAccess());
            nativeEntity.saveAsPassenger(output);
            tag = output.buildResult();
        }
        ModdedEntitySnapshot.filterTag(tag);
        TypedEntityData<EntityType<?>> data = TypedEntityData.of(nativeEntity.getType(), tag);
        item.set(DataComponents.ENTITY_DATA, data);
        return ModdedItemStack.fromNative(platform, item);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gizmos.close();
    }

    public boolean isTool(net.minecraft.world.item.ItemStack item) {
        return sessionToolProvider.isTool(ModdedItemStack.fromNative(platform, item));
    }
}
