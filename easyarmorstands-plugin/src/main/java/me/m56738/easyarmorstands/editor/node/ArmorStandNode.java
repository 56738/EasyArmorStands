package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPartButton;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPositionButton;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;

import java.util.EnumMap;

public class ArmorStandNode implements Node {
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partButtons = new EnumMap<>(ArmorStandPart.class);
    private final ArmorStandPositionButton positionButton;

    public ArmorStandNode(Session session, ArmorStandElement element) {
        PropertyContainer container = session.properties(element);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartButton partButton = new ArmorStandPartButton(session, container, part, element);
            partButtons.put(part, partButton);
        }
        positionButton = new ArmorStandPositionButton(
                session,
                ParticleColor.WHITE,
                Message.component("easyarmorstands.node.position"),
                container,
                new ArmorStandOffsetProvider(element, container),
                element);
    }

    @Override
    public void onShow(NodeShowContext context) {
        context.addButton(positionButton);
        for (ArmorStandPartButton button : partButtons.values()) {
            context.addButton(button);
        }
    }
}
