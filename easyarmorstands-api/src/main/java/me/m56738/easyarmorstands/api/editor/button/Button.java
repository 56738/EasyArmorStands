package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Button {
    static ParticleColor color(boolean focused, boolean selected, ParticleColor defaultColor) {
        if (selected) {
            if (focused) {
                return ParticleColor.AQUA;
            } else {
                return ParticleColor.BLUE;
            }
        } else {
            if (focused) {
                return ParticleColor.YELLOW;
            } else {
                return defaultColor;
            }
        }
    }

    /**
     * Updates internal properties of the button.
     */
    void update();

    /**
     * Tests whether the provided eye ray intersects with this button.
     * <p>
     * Called after {@link #update()}.
     *
     * @param ray     The eye ray.
     * @param results A consumer which should be called with each intersection.
     */
    void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results);

    /**
     * Updates the preview.
     * <p>
     * Called after {@link #update()}.
     *
     * @param focused  Whether the player is looking at this button.
     * @param selected Whether the button is currently selected.
     */
    void updatePreview(boolean focused, boolean selected);

    /**
     * Makes the preview visible.
     * <p>
     * Called after {@link #updatePreview}.
     * The preview should remain visible until {@link #hidePreview} is called.
     */
    void showPreview();

    /**
     * Makes the preview invisible.
     */
    void hidePreview();
}
