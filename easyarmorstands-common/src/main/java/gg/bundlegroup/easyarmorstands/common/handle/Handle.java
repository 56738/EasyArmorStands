package gg.bundlegroup.easyarmorstands.common.handle;

import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

import java.util.List;

/**
 * Something that can be selected by the user and edited using its manipulators.
 */
public interface Handle {
    /**
     * Refresh properties of the handle, such as its {@link #getPosition() position}.
     * Called before starting or updating a manipulator.
     */
    void update();

    /**
     * The manipulators which can be used to edit this handle.
     *
     * @return The list of manipulators.
     */
    List<Manipulator> getManipulators();

    /**
     * The position of the handle.
     * May be outdated if {@link #update} was not just called.
     *
     * @return The position.
     */
    Vector3dc getPosition();

    /**
     * The name of the handle.
     *
     * @return The name.
     */
    Component getComponent();
}
