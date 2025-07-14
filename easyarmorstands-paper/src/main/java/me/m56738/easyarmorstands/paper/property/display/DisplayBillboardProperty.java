package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;

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
        return entity.getBillboard();
    }

    @Override
    public boolean setValue(Billboard value) {
        entity.setBillboard(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
