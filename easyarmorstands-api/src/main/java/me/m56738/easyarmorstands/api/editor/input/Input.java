package me.m56738.easyarmorstands.api.editor.input;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
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
     * The category of this input.
     * <p>
     * Primary inputs are visible by default. If the player sneaks, secondary inputs will be displayed instead.
     *
     * @return the category
     */
    default Category category() {
        return Category.PRIMARY;
    }

    /**
     * Called when this input is triggered.
     */
    void execute(@NotNull ClickContext context);
}
