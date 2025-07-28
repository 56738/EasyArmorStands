package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import org.jetbrains.annotations.NotNull;

public interface DefaultEntityElement extends EntityElement {
    @Override
    @NotNull PropertyRegistry getProperties();
}
