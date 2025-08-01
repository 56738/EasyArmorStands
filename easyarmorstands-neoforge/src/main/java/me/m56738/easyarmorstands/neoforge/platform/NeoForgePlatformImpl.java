package me.m56738.easyarmorstands.neoforge.platform;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedCommandSender;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.neoforge.api.event.element.DefaultEntityElementInitializeEvent;
import me.m56738.easyarmorstands.neoforge.api.event.element.ElementCreateEvent;
import me.m56738.easyarmorstands.neoforge.api.event.element.ElementDestroyEvent;
import me.m56738.easyarmorstands.neoforge.api.event.element.ElementDiscoverEvent;
import me.m56738.easyarmorstands.neoforge.api.event.element.ElementPropertyChangeEvent;
import me.m56738.easyarmorstands.neoforge.api.event.element.ElementPropertyChangeEvent.PropertyChange;
import me.m56738.easyarmorstands.neoforge.api.event.element.ElementSelectEvent;
import me.m56738.easyarmorstands.neoforge.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.neoforge.api.event.session.SessionStopEvent;
import me.m56738.easyarmorstands.neoforge.api.platform.NeoForgePlatform;
import me.m56738.easyarmorstands.neoforge.config.NeoForgeConfiguration;
import me.m56738.easyarmorstands.neoforge.permission.NeoForgePermissions;
import me.m56738.easyarmorstands.neoforge.platform.entity.NeoForgeCommandSenderImpl;
import me.m56738.easyarmorstands.neoforge.platform.entity.NeoForgePlayerImpl;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;

import java.util.Map;

public class NeoForgePlatformImpl extends ModdedPlatformImpl implements NeoForgePlatform {
    private final NeoForgePermissions permissions;
    private final Configuration configuration;

    public NeoForgePlatformImpl(MinecraftServer server, NeoForgePermissions permissions) {
        super(server);
        this.permissions = permissions;
        this.configuration = new NeoForgeConfiguration();
    }

    public NeoForgePermissions getPermissions() {
        return permissions;
    }

    @Override
    public ModdedPlayer getPlayer(ServerPlayer nativePlayer) {
        return new NeoForgePlayerImpl(this, nativePlayer);
    }

    @Override
    public ModdedCommandSender getCommandSender(CommandSourceStack stack) {
        return new NeoForgeCommandSenderImpl(stack);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean canDiscoverElement(Player player, EditableElement element) {
        return !NeoForge.EVENT_BUS.post(new ElementDiscoverEvent(player, element)).isCanceled();
    }

    @Override
    public boolean canSelectElement(Player player, EditableElement element) {
        return !NeoForge.EVENT_BUS.post(new ElementSelectEvent(player, element)).isCanceled();
    }

    @Override
    public boolean canCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return !NeoForge.EVENT_BUS.post(new ElementCreateEvent(player, type, properties)).isCanceled();
    }

    @Override
    public boolean canDestroyElement(Player player, DestroyableElement element) {
        return !NeoForge.EVENT_BUS.post(new ElementDestroyEvent(player, element)).isCanceled();
    }

    @Override
    public <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value) {
        PropertyChange<T> change = new PropertyChange<>(property, value);
        return !NeoForge.EVENT_BUS.post(new ElementPropertyChangeEvent(player, element, change)).isCanceled();
    }

    @Override
    public void onStartSession(Session session) {
        NeoForge.EVENT_BUS.post(new SessionStartEvent(session));
    }

    @Override
    public void onStopSession(Session session) {
        NeoForge.EVENT_BUS.post(new SessionStopEvent(session));
    }

    @Override
    public void onElementInitialize(DefaultEntityElement element) {
        NeoForge.EVENT_BUS.post(new DefaultEntityElementInitializeEvent(element));
    }

    @Override
    public void registerPermission(PermissionNode<Boolean> permission) {
        permissions.register(permission, Map.of());
    }
}
