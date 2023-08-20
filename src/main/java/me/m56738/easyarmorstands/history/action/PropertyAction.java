package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.ElementReference;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

public class PropertyAction<T> implements Action {
    private final ElementReference elementReference;
    private final PropertyType<T> propertyType;
    private final T oldValue;
    private final T newValue;

    public PropertyAction(ElementReference elementReference, PropertyType<T> propertyType, T oldValue, T newValue) {
        this.elementReference = elementReference;
        this.propertyType = propertyType;
        this.oldValue = propertyType.cloneValue(oldValue);
        this.newValue = propertyType.cloneValue(newValue);
    }

    @Override
    public boolean execute(ChangeContext context) {
        return tryChange(newValue, context);
    }

    @Override
    public boolean undo(ChangeContext context) {
        return tryChange(oldValue, context);
    }

    private boolean tryChange(T value, ChangeContext context) {
        Element element = elementReference.getElement();
        if (element == null) {
            return false;
        }
        PropertyContainer properties = PropertyContainer.identified(context, element.getProperties());
        Property<T> property = properties.getOrNull(propertyType);
        if (property == null) {
            return false;
        }
        return property.setValue(value);
    }

    @Override
    public Component describe() {
        return Component.text()
                .content("Changed ")
                .append(propertyType.getName().colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" from "))
                .append(propertyType.getValueComponent(oldValue).colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" to "))
                .append(propertyType.getValueComponent(newValue).colorIfAbsent(NamedTextColor.WHITE))
                .color(NamedTextColor.GRAY)
                .build();
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        elementReference.onEntityReplaced(oldId, newId);
    }
}
