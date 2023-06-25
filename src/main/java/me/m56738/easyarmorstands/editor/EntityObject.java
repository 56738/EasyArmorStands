package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.node.DefaultEntityNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.property.EntityPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EntityObject implements EditorObject {
    private final Entity entity;
    private final Map<String, Property<?>> properties = new HashMap<>();
    private final Map<String, Property<?>> propertiesView = Collections.unmodifiableMap(properties);

    public EntityObject(Entity entity) {
        this.entity = entity;
        for (EntityPropertyType<?> type : EasyArmorStands.getInstance().getEntityPropertyTypeRegistry().getTypes()) {
            Property<?> property = type.bind(entity);
            if (property != null) {
                // TODO better name
                properties.put(property.getType().getClass().getSimpleName(), property);
            }
        }
    }

    @Override
    public Node createNode(Session session) {
        return new DefaultEntityNode(session, entity);
    }

    @Override
    public @UnmodifiableView Map<String, Property<?>> getProperties() {
        return propertiesView;
    }
}
