package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPartButton;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPartButtonHandler;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPositionButtonHandler;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;

public class ArmorStandNode implements Node {
    private final Session session;
    private final ArmorStandElement element;
    private final PropertyContainer properties;

    public ArmorStandNode(Session session, ArmorStandElement element) {
        this.session = session;
        this.element = element;
        this.properties = session.properties(element);
    }

    @Override
    public void onShow(NodeShowContext context) {
        context.addButton(createPositionButton(), new ArmorStandPositionButtonHandler(
                session,
                Message.component("easyarmorstands.node.position"),
                properties,
                new ArmorStandOffsetProvider(element, properties),
                element));

        for (ArmorStandPart part : ArmorStandPart.values()) {
            context.addButton(createPartButton(part),
                    new ArmorStandPartButtonHandler(session, element, properties, part));
        }
    }

    private Button createPositionButton() {
        PointButton button = new PointButton(session,
                new EntityPositionProvider(properties, new ArmorStandOffsetProvider(element, properties)),
                new ArmorStandRotationProvider(properties));
        button.setPriority(1);
        return button;
    }

    private Button createPartButton(ArmorStandPart part) {
        return new ArmorStandPartButton(session, properties, part, element);
    }
}
