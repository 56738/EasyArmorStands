package gg.bundlegroup.easyarmorstands.common.manipulator;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

/**
 * A tool which can be used to manipulate a handle.
 */
public interface Manipulator {
    /**
     * Called while the manipulator is not selected.
     * Refreshes properties which depend on the armor stand or player.
     */
    void refresh();

    /**
     * Called when the manipulator is selected.
     *
     * @param cursor The initial position of the cursor.
     *               Usually the {@link #getLookTarget() position of the clicked handle}.
     */
    void start(Vector3dc cursor);

    /**
     * Called while the manipulator is active.
     * Updates the armor stand depending on the current value of the manipulator.
     *
     * @return The value.
     */
    Component update();

    /**
     * Aborts the manipulator.
     * Reverts changed properties of the armor stand to what they were before {@link #start} was called.
     */
    void abort();

    /**
     * Called while the manipulator is inactive.
     * Shows the clickable handles.
     */
    void showHandles();

    /**
     * Called while the manipulator is active.
     * Shows its current state.
     */
    void show();

    Vector3dc getTarget();

    Vector3dc getLookTarget();

    /**
     * The name of the manipulator.
     * Should be very short, for example "Move" or "Y".
     *
     * @return The name.
     */
    Component component();
}
