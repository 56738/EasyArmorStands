package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.editor.EditableObjectReference;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

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
    public boolean execute(Player player) {
        return tryChange(newValue, player);
    }

    @Override
    public boolean undo(Player player) {
        return tryChange(oldValue, player);
    }

    private boolean tryChange(T value, Player player) {
        EditableObject editableObject = objectReference.restoreObject();
        if (editableObject == null) {
            return false;
        }
        PropertyContainer properties = PropertyContainer.identified(editableObject.properties(), player);
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
        objectReference.onEntityReplaced(oldId, newId);
    }
}
