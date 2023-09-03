package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.item.ItemTemplate;

import java.util.List;

public class GravityToggleButton extends BooleanToggleButton {
    private final Property<Boolean> canTickProperty;
    private final List<String> canTickWarning;

    public GravityToggleButton(Property<Boolean> property, PropertyContainer container, ItemTemplate item, List<String> canTickWarning) {
        super(property, container, item);
        this.canTickProperty = container.getOrNull(ArmorStandPropertyTypes.CAN_TICK);
        this.canTickWarning = canTickWarning;
    }

    @Override
    protected ItemTemplate prepareTemplate(ItemTemplate template) {
        template = super.prepareTemplate(template);
        if (canTickProperty != null && property.getValue() && !canTickProperty.getValue()) {
            template = template.appendLore(canTickWarning);
        }
        return template;
    }

    @Override
    public Boolean getNextValue() {
        boolean value = property.getValue();
        if (canTickProperty != null && value && !canTickProperty.getValue()) {
            return true;
        }
        return !value;
    }

    @Override
    protected boolean setValue(Boolean value) {
        if (!super.setValue(value)) {
            return false;
        }
        if (canTickProperty != null && value) {
            canTickProperty.setValue(true);
        }
        return true;
    }
}
