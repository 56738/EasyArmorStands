package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.editor.EditableObjectReference;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

public class PropertyAction<T> implements Action {
    private final EditableObjectReference objectReference;
    private final PropertyType<T> propertyType;
    private final T oldValue;
    private final T newValue;

    public PropertyAction(EditableObjectReference objectReference, PropertyType<T> propertyType, T oldValue, T newValue) {
        this.objectReference = objectReference;
        this.propertyType = propertyType;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public boolean execute() {
        return tryChange(newValue);
    }

    @Override
    public boolean undo() {
        return tryChange(oldValue);
    }

    private boolean tryChange(T value) {
        EditableObject editableObject = objectReference.restoreObject();
        if (editableObject == null) {
            return false;
        }
        Property<T> property = editableObject.properties().get(propertyType);
        if (property == null) {
            return false;
        }
        if (!property.isValid()) {
            return false;
        }
        property.setValue(value);
        return true;
    }

    @Override
    public Component describe() {
        return Component.text()
                .content("Changed ")
                .append(propertyType.getDisplayName().colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" from "))
                .append(propertyType.getValueComponent(oldValue).colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" to "))
                .append(propertyType.getValueComponent(newValue).colorIfAbsent(NamedTextColor.WHITE))
                .color(NamedTextColor.GRAY)
                .build();
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
    }
}
