package me.m56738.easyarmorstands.common.history.action;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementReference;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.common.property.PermissionCheckedPropertyContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PropertyAction<T> implements Action {
    private final CommonPlatform platform;
    private final ElementReference elementReference;
    private final PropertyType<T> propertyType;
    private final T oldValue;
    private final T newValue;

    public PropertyAction(CommonPlatform platform, ElementReference elementReference, PropertyType<T> propertyType, T oldValue, T newValue) {
        this.platform = platform;
        this.elementReference = elementReference;
        this.propertyType = propertyType;
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
        Element element = elementReference.getElement();
        if (element == null) {
            return false;
        }
        PropertyContainer properties = new PermissionCheckedPropertyContainer(platform, element, context.getPlayer());
        Property<T> property = properties.getOrNull(propertyType);
        if (property == null) {
            return false;
        }
        return property.setValue(value);
    }

    @Override
    public Component describe() {
        return Message.component("easyarmorstands.history.changed-property",
                propertyType.name().colorIfAbsent(NamedTextColor.WHITE),
                propertyType.getValueComponent(oldValue).colorIfAbsent(NamedTextColor.WHITE),
                propertyType.getValueComponent(newValue).colorIfAbsent(NamedTextColor.WHITE));
    }

    @Override
    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        elementReference.onEntityReplaced(oldId, newId);
    }
}
