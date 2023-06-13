package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.property.ChangeContext;
import me.m56738.easyarmorstands.property.LegacyEntityPropertyType;
import me.m56738.easyarmorstands.property.PropertyChange;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;

public class EntityPropertyAction<E extends Entity, T> extends EntityAction<E> {
    private final LegacyEntityPropertyType<E, T> property;
    private final T oldValue;
    private final T newValue;

    public EntityPropertyAction(E entity, LegacyEntityPropertyType<E, T> property, T oldValue, T newValue) {
        super(entity);
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
        E entity = findEntity();
        return context.tryChange(new PropertyChange<>(property.bind(entity), value));
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
