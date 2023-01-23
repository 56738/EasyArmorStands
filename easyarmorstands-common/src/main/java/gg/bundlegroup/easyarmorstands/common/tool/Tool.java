package gg.bundlegroup.easyarmorstands.common.tool;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

/**
 * A tool which can be used to modify bone.
 */
public interface Tool {
    /**
     * Called while the tool is not selected.
     * Refreshes properties which depend on the armor stand or player.
     */
    void refresh();

    /**
     * Called when the tool is selected.
     *
     * @param cursor The initial position of the cursor.
     *               Usually the {@link #getLookTarget() position where the handle was clicked}.
     */
    void start(Vector3dc cursor);

    /**
     * Called while the tool is active.
     * Updates the armor stand depending on the current value of the tool.
     *
     * @return The value.
     */
    Component update();

    /**
     * Aborts the tool.
     * Reverts changed properties of the armor stand to what they were before {@link #start} was called.
     */
    void abort();

    /**
     * Called while the tool is inactive.
     * Shows the clickable handles.
     */
    void showHandles();

    /**
     * Called while the tool is active.
     * Shows its current state.
     */
    void show();

    /**
     * Returns the initial cursor that should be used if this tool is activated using a command.
     *
     * @return The initial cursor.
     */
    Vector3dc getTarget();

    /**
     * Returns the position of the handle the player is looking at.
     *
     * @return The position of the handle the player is looking at, or null if none.
     */
    Vector3dc getLookTarget();

    /**
     * The name of this tool.
     * Should be very short, for example "Move" or "Y".
     *
     * @return The name.
     */
    Component component();
}
