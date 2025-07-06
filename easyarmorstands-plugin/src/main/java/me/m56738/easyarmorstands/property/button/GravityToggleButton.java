package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;

import java.util.List;

public class GravityToggleButton extends BooleanToggleButton {
    private final Property<Boolean> untrackedCanTickProperty;
    private final List<String> canTickWarning;

    public GravityToggleButton(Element element, PropertyType<Boolean> type, SimpleItemTemplate item, List<String> canTickWarning) {
        super(element, type, item);
        this.untrackedCanTickProperty = element.getProperties().getOrNull(ArmorStandPropertyTypes.CAN_TICK);
        this.canTickWarning = canTickWarning;
    }

    @Override
    protected SimpleItemTemplate prepareTemplate(SimpleItemTemplate template) {
        template = super.prepareTemplate(template);
        if (untrackedCanTickProperty != null && getUntrackedProperty().getValue() && !untrackedCanTickProperty.getValue()) {
            template = template.appendLore(canTickWarning);
        }
        return template;
    }

    @Override
    public Boolean getNextValue() {
        boolean value = getUntrackedProperty().getValue();
        if (untrackedCanTickProperty != null && value && !untrackedCanTickProperty.getValue()) {
            return true;
        }
        return !value;
    }

    @Override
    protected boolean setValue(ChangeContext context, Boolean value) {
        if (!super.setValue(context, value)) {
            return false;
        }
        Property<Boolean> canTickProperty = context.getProperties(getElement()).getOrNull(ArmorStandPropertyTypes.CAN_TICK);
        if (canTickProperty != null && value) {
            canTickProperty.setValue(true);
        }
        return true;
    }
}
