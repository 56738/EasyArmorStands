package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.property.ChangeContext;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyChange;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

public class PropertyAction<T> implements Action {
    private final Property<T> property;
    private final T oldValue;
    private final T newValue;

    public PropertyAction(Property<T> property, T oldValue, T newValue) {
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
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
        if (!property.isValid()) {
            return false;
        }
        return context.tryChange(new PropertyChange<>(property, value));
    }

    @Override
    public Component describe() {
        return Component.text()
                .content("Changed ")
                .append(property.getDisplayName().colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" from "))
                .append(property.getValueComponent(oldValue).colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" to "))
                .append(property.getValueComponent(newValue).colorIfAbsent(NamedTextColor.WHITE))
                .color(NamedTextColor.GRAY)
                .build();
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
    }
}
