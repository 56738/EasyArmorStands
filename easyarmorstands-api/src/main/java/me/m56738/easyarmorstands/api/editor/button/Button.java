package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.EyeRay;

import java.util.function.Consumer;

public interface Button {
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
    void intersect(EyeRay ray, Consumer<ButtonResult> results);

    /**
     * Updates the preview.
     * <p>
     * Called after {@link #update()}.
     *
     * @param focused Whether the player is looking at this button.
     */
    void updatePreview(boolean focused);

    /**
     * Makes the preview visible.
     * <p>
     * Called after {@link #updatePreview(boolean)}.
     * The preview should remain visible until {@link #hidePreview()} is called.
     */
    void showPreview();

    /**
     * Makes the preview invisible.
     */
    void hidePreview();
}
