package me.m56738.easyarmorstands.fabric.platform;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.fabric.api.EasyArmorStandsEvents;
import me.m56738.easyarmorstands.fabric.api.EasyArmorStandsEvents.PropertyChange;
import me.m56738.easyarmorstands.fabric.config.FabricConfiguration;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedBlockDataImpl;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.server.MinecraftServer;

public class FabricPlatformImpl extends ModdedPlatformImpl {
    private final FabricConfiguration configuration;

    public FabricPlatformImpl(MinecraftServer server) {
        super(server);
        this.configuration = new FabricConfiguration();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean canDiscoverElement(Player player, EditableElement element) {
        return EasyArmorStandsEvents.DISCOVER_ELEMENT.invoker().onDiscoverElement(player, element);
    }

    @Override
    public boolean canSelectElement(Player player, EditableElement element) {
        return EasyArmorStandsEvents.SELECT_ELEMENT.invoker().onSelectElement(player, element);
    }

    @Override
    public boolean canCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return EasyArmorStandsEvents.CREATE_ELEMENT.invoker().onCreateElement(player, type, properties);
    }

    @Override
    public boolean canDestroyElement(Player player, DestroyableElement element) {
        return EasyArmorStandsEvents.DESTROY_ELEMENT.invoker().onDestroyElement(player, element);
    }

    @Override
    public <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value) {
        PropertyChange<T> change = new PropertyChange<>(property, value);
        return EasyArmorStandsEvents.CHANGE_PROPERTY.invoker().onChangeProperty(player, element, change);
    }

    @Override
    public void onStartSession(Session session) {
        EasyArmorStandsEvents.SESSION_STARTED.invoker().onSessionStarted(session);
    }

    @Override
    public void onStopSession(Session session) {
        EasyArmorStandsEvents.SESSION_STOPPED.invoker().onSessionStopped(session);
    }

    @Override
    public void onElementInitialize(DefaultEntityElement element) {
        EasyArmorStandsEvents.INITIALIZE_DEFAULT_ELEMENT.invoker().onInitializeDefaultElement(element);
    }
}
