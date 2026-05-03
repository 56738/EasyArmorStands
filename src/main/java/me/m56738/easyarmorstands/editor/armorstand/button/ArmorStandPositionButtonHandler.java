package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandlerContext;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.layer.ArmorStandPositionLayer;
import me.m56738.easyarmorstands.editor.input.selection.SelectElementInput;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import net.kyori.adventure.text.Component;

public class ArmorStandPositionButtonHandler implements ButtonHandler {
    private final Session session;
    private final Component name;
    private final PropertyContainer container;
    private final OffsetProvider offsetProvider;
    private final ArmorStandElement element;

    public ArmorStandPositionButtonHandler(Session session, Component name, PropertyContainer container, OffsetProvider offsetProvider, ArmorStandElement element) {
        this.session = session;
        this.name = name;
        this.container = container;
        this.offsetProvider = offsetProvider;
        this.element = element;
    }

    @Override
    public void onUpdate(ButtonHandlerContext context) {
        context.addInput(new SelectElementInput(session, element, this::createLayer));
    }

    @Override
    public Component getName() {
        return name;
    }

    private Layer createLayer() {
        return new ArmorStandPositionLayer(session, container, offsetProvider, element);
    }
}
