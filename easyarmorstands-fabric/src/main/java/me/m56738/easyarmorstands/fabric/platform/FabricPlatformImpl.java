package me.m56738.easyarmorstands.fabric.platform;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.gizmo.api.GizmoFactory;
import net.minecraft.server.MinecraftServer;

public class FabricPlatformImpl extends ModdedPlatformImpl {
    public FabricPlatformImpl(MinecraftServer server) {
        super(server);
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public GizmoFactory getGizmoFactory(Player player) {
        return null;
    }

    @Override
    public BlockData createBlockData(String input) {
        return null;
    }

    @Override
    public boolean canDiscoverElement(Player player, EditableElement element) {
        return false;
    }

    @Override
    public boolean canSelectElement(Player player, EditableElement element) {
        return false;
    }

    @Override
    public boolean canCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return false;
    }

    @Override
    public boolean canDestroyElement(Player player, DestroyableElement element) {
        return false;
    }

    @Override
    public <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value) {
        return false;
    }

    @Override
    public EntityType getArmorStandType() {
        return null;
    }

    @Override
    public EntityType getBlockDisplayType() {
        return null;
    }

    @Override
    public EntityType getItemDisplayType() {
        return null;
    }

    @Override
    public EntityType getTextDisplayType() {
        return null;
    }

    @Override
    public EntityType getInteractionType() {
        return null;
    }

    @Override
    public void onStartSession(Session session) {
    }

    @Override
    public void onStopSession(Session session) {
    }

    @Override
    public void onElementInitialize(DefaultEntityElement element) {

    }
}
