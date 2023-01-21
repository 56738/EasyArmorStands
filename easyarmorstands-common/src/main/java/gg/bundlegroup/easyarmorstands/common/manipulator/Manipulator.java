package gg.bundlegroup.easyarmorstands.common.manipulator;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

/**
 * A tool which can be used to manipulate a handle.
 */
public interface Manipulator {
    /**
     * Called when the manipulator is selected.
     *
     * @param cursor The current position of the cursor.
     */
    void start(Vector3dc cursor);

    /**
     * Called every tick while the manipulator is selected.
     *
     * @param freeLook Whether the editor is in free-look mode, i.e., player movement should not affect the cursor.
     */
    void update(boolean freeLook);

    /**
     * The position of the cursor, as of the last call to {@link #update}.
     *
     * @return The current position of the cursor.
     */
    Vector3dc getCursor();

    /**
     * The name of the manipulator.
     * Should be very short, for example "Move" or "Y".
     *
     * @return The name.
     */
    Component getComponent();
}
