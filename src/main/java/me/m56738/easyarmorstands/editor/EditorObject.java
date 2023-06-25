package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.session.Session;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;

public interface EditorObject {
    Node createNode(Session session);

    @UnmodifiableView Map<String, Property<?>> getProperties();
}
