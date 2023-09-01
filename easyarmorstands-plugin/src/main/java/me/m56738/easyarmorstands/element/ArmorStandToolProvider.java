package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.EntityYawRotationProvider;

public class ArmorStandToolProvider extends SimpleEntityToolProvider {
    public ArmorStandToolProvider(PropertyContainer properties) {
        super(properties);
        rotationProvider = new EntityYawRotationProvider(properties);
    }
}
