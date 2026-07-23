package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.ReferenceProvider;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.fancyholograms.FancyHologramsAddon;
import me.m56738.easyarmorstands.fancyholograms.editor.layer.HologramRootLayer;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class HologramElement implements SelectableElement, DestroyableElement, EditableElement, MenuElement {
    private final EasyArmorStandsCommon eas;
    private final HologramElementType type;
    private final HologramManager manager;
    private final Hologram hologram;
    private final FancyHologramsAddon addon;
    private final PropertyRegistry properties = PropertyRegistry.create(this);

    public HologramElement(EasyArmorStandsCommon eas, HologramElementType type, HologramManager manager, Hologram hologram, FancyHologramsAddon addon) {
        this.eas = eas;
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
                new EntityPositionProvider(properties, getOffsetProvider(properties)),
                new EntityRotationProvider(properties));
    }

    @Override
    public @NotNull Layer createLayer(@NotNull Session session) {
        return new HologramRootLayer(eas, session, this);
    }

    @Override
    public @NotNull ToolProvider getTools(@NotNull PropertyContainer properties) {
        return new HologramToolProvider(eas, properties, getOffsetProvider(properties));
    }

    private OffsetProvider getOffsetProvider(PropertyContainer properties) {
        if (hologram.getData() instanceof BlockHologramData) {
            return new BlockHologramOffsetProvider(properties);
        } else {
            return OffsetProvider.zero();
        }
    }

    @Override
    public void openMenu(@NotNull Player player) {
        eas.openElementMenu(player, this);
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
    public @NotNull HologramElementReference getReference(ReferenceProvider provider) {
        return new HologramElementReference(type, manager, hologram.getName());
    }

    @Override
    public boolean isValid() {
        return manager.getHologram(hologram.getName()).isPresent();
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return BoundingBox.of(PaperAdapter.fromNative(hologram.getData().getLocation()).position());
    }
}
