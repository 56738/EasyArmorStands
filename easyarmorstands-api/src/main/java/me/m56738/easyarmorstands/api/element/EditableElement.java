package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EditableElement extends Element {
    boolean canEdit(Player player);

    @Contract(pure = true)
    @NotNull ToolProvider getTools(PropertyContainer properties);

    @Contract(pure = true)
    @NotNull BoundingBox getBoundingBox();
}
