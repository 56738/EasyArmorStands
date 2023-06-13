package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.property.ChangeContext;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyChange;
import me.m56738.easyarmorstands.property.PropertyReference;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

public class PropertyAction<T> implements Action {
    private final PropertyType<T> type;
    private final T oldValue;
    private final T newValue;
    private PropertyReference<T> reference;

    public PropertyAction(PropertyType<T> type, PropertyReference<T> reference, T oldValue, T newValue) {
        this.type = type;
        this.reference = reference;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public PropertyAction(Property<T> property, T oldValue, T newValue) {
        this(property.getType(), property.asReference(), oldValue, newValue);
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
        Property<T> property = reference.restore();
        if (property == null) {
            return false;
        }
        return context.tryChange(new PropertyChange<>(property, value));
    }

    @Override
    public Component describe() {
        return Component.text()
                .content("Changed ")
                .append(type.getDisplayName().colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" from "))
                .append(type.getValueComponent(oldValue).colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" to "))
                .append(type.getValueComponent(newValue).colorIfAbsent(NamedTextColor.WHITE))
                .color(NamedTextColor.GRAY)
                .build();
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        reference = reference.replaceEntity(oldId, newId);
    }
}
