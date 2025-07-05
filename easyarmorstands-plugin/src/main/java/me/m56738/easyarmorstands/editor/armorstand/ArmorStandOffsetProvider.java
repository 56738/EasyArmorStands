package me.m56738.easyarmorstands.editor.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;

public class ArmorStandOffsetProvider implements OffsetProvider {
    private final ArmorStandElement element;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandOffsetProvider(ArmorStandElement element, PropertyContainer container) {
        this.element = element;
        this.sizeProperty = container.get(ArmorStandPropertyTypes.SIZE);
    }

    @Override
    public Vector3dc getOffset() {
        double offset = 1.25 * element.getScale();
        if (sizeProperty.getValue().isSmall()) {
            offset /= 2;
        }
        return new Vector3d(0, offset, 0);
    }
}
