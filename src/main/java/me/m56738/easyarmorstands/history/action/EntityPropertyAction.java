package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;

public class EntityPropertyAction<E extends Entity, T> extends EntityAction<E> {
    private final EntityProperty<E, T> property;
    private final T oldValue;
    private final T newValue;

    public EntityPropertyAction(E entity, EntityProperty<E, T> property, T oldValue, T newValue) {
        super(entity);
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public boolean execute() {
        return setValue(newValue);
    }

    @Override
    public boolean undo() {
        return setValue(oldValue);
    }

    private boolean setValue(T value) {
        E entity = findEntity();
        property.setValue(entity, value);
        return true;
    }

    @Override
    public Component describe() {
        return Component.text()
                .content("Changed ")
                .append(property.getDisplayName().colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" from "))
                .append(property.getValueName(oldValue).colorIfAbsent(NamedTextColor.WHITE))
                .append(Component.text(" to "))
                .append(property.getValueName(newValue).colorIfAbsent(NamedTextColor.WHITE))
                .color(NamedTextColor.GRAY)
                .build();
    }
}
