package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import me.m56738.easyarmorstands.fancyholograms.FancyHologramsAddon;
import me.m56738.easyarmorstands.fancyholograms.editor.node.HologramRootNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class HologramElement implements SelectableElement, DestroyableElement, EditableElement, MenuElement {
    private final HologramElementType type;
    private final HologramManager manager;
    private final Hologram hologram;
    private final FancyHologramsAddon addon;
    private final PropertyRegistry properties = new PropertyRegistry();

    public HologramElement(HologramElementType type, HologramManager manager, Hologram hologram, FancyHologramsAddon addon) {
        this.type = type;
        this.manager = manager;
        this.hologram = hologram;
        this.addon = addon;
    }

    @Override
    public boolean canEdit(@NotNull Player player) {
        return player.hasPermission(Permissions.FANCYHOLOGRAMS_EDIT);
    }

    @Override
    public boolean canDestroy(@NotNull Player player) {
        return player.hasPermission(Permissions.FANCYHOLOGRAMS_DESTROY);
    }

    @Override
    public void destroy() {
        manager.removeHologram(hologram);
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        return new PointButton(session,
                new EntityPositionProvider(properties),
                new EntityRotationProvider(properties));
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new HologramRootNode(session, this);
    }

    @Override
    public @NotNull ToolProvider getTools(@NotNull ChangeContext changeContext) {
        return new HologramToolProvider(this, changeContext);
    }

    @Override
    public void openMenu(@NotNull Player player) {
        MenuFactory factory = addon.getMenuFactory(hologram.getData());
        if (factory != null) {
            Session session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(player);
            EasyArmorStandsPlugin.getInstance().openMenu(player, session, factory, this);
        }
    }

    @Override
    public @NotNull Component getName() {
        return Component.text(hologram.getName());
    }

    @Override
    public @NotNull HologramElementType getType() {
        return type;
    }

    @Override
    public @NotNull PropertyRegistry getProperties() {
        return properties;
    }

    @Override
    public @NotNull HologramElementReference getReference() {
        return new HologramElementReference(type, manager, hologram.getName());
    }

    @Override
    public boolean isValid() {
        return manager.getHologram(hologram.getName()).isPresent();
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return BoundingBox.of(hologram.getData().getLocation().toVector().toVector3d());
    }

    public @NotNull Hologram getHologram() {
        return hologram;
    }
}
