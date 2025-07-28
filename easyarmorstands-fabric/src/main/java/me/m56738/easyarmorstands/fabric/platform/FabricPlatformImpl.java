package me.m56738.easyarmorstands.fabric.platform;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.gizmo.api.GizmoFactory;
import net.minecraft.server.MinecraftServer;
import org.incendo.cloud.parser.ParserDescriptor;

public class FabricPlatformImpl extends ModdedPlatformImpl {
    public FabricPlatformImpl(MinecraftServer server, String modVersion) {
        super(server, modVersion);
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
    public <C> ParserDescriptor<C, Location> getLocationParser() {
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
}
