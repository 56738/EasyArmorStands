package me.m56738.easyarmorstands.context;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.history.History;
import net.kyori.adventure.permission.PermissionChecker;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface ChangeContext {
    @Contract(pure = true)
    @NotNull PermissionChecker permissions();

    @Contract(pure = true)
    @NotNull History history();

    @Contract(pure = true)
    @NotNull Locale locale();

    boolean canCreateElement(ElementType type, PropertyContainer properties);

    boolean canDestroyElement(DestroyableElement element);

    <T> boolean canChangeProperty(Element element, Property<T> property, T value);
}
