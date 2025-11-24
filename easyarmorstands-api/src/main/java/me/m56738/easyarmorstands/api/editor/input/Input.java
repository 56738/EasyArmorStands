package me.m56738.easyarmorstands.api.editor.input;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public interface Input {
    /**
     * Returns the name of this input.
     *
     * @return the name
     */
    @NotNull Component name();

    /**
     * Returns the style of the input.
     *
     * @return the style
     */
    @NotNull Style style();

    /**
     * The button which triggers this input.
     *
     * @return the button
     */
    ClickContext.@NotNull Type clickType();

    /**
     * Whether this input can only be triggered while the player is sneaking.
     *
     * @return true if sneaking is required
     */
    default boolean requireSneak() {
        return false;
    }

    /**
     * Whether this input may be triggered while the player is sneaking.
     *
     * @return false if sneaking should prevent triggering this input
     */
    default boolean allowSneak() {
        return true;
    }

    /**
     * Called when this input is triggered.
     */
    void execute(@NotNull ClickContext context);
}
