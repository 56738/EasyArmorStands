package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.platform.entity.display.Billboard;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;

public class DisplayBillboardProperty implements Property<Billboard> {
    private final Display entity;

    public DisplayBillboardProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Billboard> getType() {
        return DisplayPropertyTypes.BILLBOARD;
    }

    @Override
    public Billboard getValue() {
        return switch (entity.getBillboard()) {
            case FIXED -> Billboard.FIXED;
            case VERTICAL -> Billboard.VERTICAL;
            case HORIZONTAL -> Billboard.HORIZONTAL;
            case CENTER -> Billboard.CENTER;
        };
    }

    @Override
    public boolean setValue(Billboard value) {
        entity.setBillboard(switch (value) {
            case FIXED -> Display.Billboard.FIXED;
            case VERTICAL -> Display.Billboard.VERTICAL;
            case HORIZONTAL -> Display.Billboard.HORIZONTAL;
            case CENTER -> Display.Billboard.CENTER;
        });
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
