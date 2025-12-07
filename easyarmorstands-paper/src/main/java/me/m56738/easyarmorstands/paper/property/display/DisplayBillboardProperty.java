package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.platform.entity.display.Billboard;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;

public class DisplayBillboardProperty extends EntityProperty<Display, Billboard> {
    public DisplayBillboardProperty(Display entity) {
        super(entity);
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
}
