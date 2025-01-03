package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBoxProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An element which can be edited using tools.
 */
public interface EditableElement extends Element, BoundingBoxProvider {
    /**
     * Checks whether a player is allowed to edit this element.
     *
     * @param player the player
     * @return whether the element may be edited
     */
    @Contract(pure = true)
    boolean canEdit(@NotNull Player player);

    /**
     * Returns the tools which can be used to edit this element.
     *
     * @param properties the properties which the tools should modify
     * @return the tools
     */
    @Contract(pure = true)
    @NotNull ToolProvider getTools(@NotNull PropertyContainer properties);
}
