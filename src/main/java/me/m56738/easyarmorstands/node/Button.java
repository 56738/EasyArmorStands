package me.m56738.easyarmorstands.node;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface Button extends NodeFactory {
    /**
     * Updates internal properties of the button.
     */
    void update();

    /**
     * Updates the {@link #getLookTarget() look target} and {@link #getLookPriority() priority}.
     * <p>
     * Called after {@link #update()}.
     *
     * @param eyes   The position of the eyes (the beginning of the eye ray).
     * @param target The position where the player is looking (the end of the eye ray).
     */
    void updateLookTarget(Vector3dc eyes, Vector3dc target);

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

    /**
     * Returns the location where the eye ray intersects this node.
     *
     * @return Position of the intersection with the eye ray, or null if there is none.
     */
    @Nullable Vector3dc getLookTarget();

    /**
     * Returns the priority of this button.
     * If the player is looking at multiple buttons, the button with the highest priority wins.
     *
     * @return The priority.
     */
    default int getLookPriority() {
        return 0;
    }

    Component getName();

    /**
     * Creates a node that should be entered when this button is clicked.
     *
     * @return The node which can be entered using this button.
     */
    @Override
    @Nullable Node createNode();
}
