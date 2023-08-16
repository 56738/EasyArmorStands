package me.m56738.easyarmorstands.context;

import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.property.PropertyContainer;
import net.kyori.adventure.permission.PermissionChecker;
import org.jetbrains.annotations.NotNull;

public interface ChangeContext {
    @NotNull PermissionChecker permissions();

    @NotNull History history();

    boolean canCreateElement(ElementType type, PropertyContainer properties);

    boolean canDestroyElement(DestroyableElement element);
}
