package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandlerContext;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.armorstand.layer.ArmorStandPartLayer;
import me.m56738.easyarmorstands.editor.input.selection.SelectElementInput;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import net.kyori.adventure.text.Component;

public class ArmorStandPartButtonHandler implements ButtonHandler {
    private final Session session;
    private final ArmorStandElement element;
    private final PropertyContainer container;
    private final ArmorStandPart part;
    private final ArmorStandPartInfo partInfo;

    public ArmorStandPartButtonHandler(Session session, ArmorStandElement element, PropertyContainer container, ArmorStandPart part) {
        this.session = session;
        this.element = element;
        this.container = container;
        this.part = part;
        this.partInfo = ArmorStandPartInfo.of(part);
    }

    @Override
    public void onUpdate(ButtonHandlerContext context) {
        context.addInput(new SelectElementInput(session, element, this::createLayer));
    }

    @Override
    public Component getName() {
        return partInfo.getDisplayName();
    }

    private Layer createLayer() {
        return new ArmorStandPartLayer(session, container, part, element);
    }
}
