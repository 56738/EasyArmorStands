package gg.bundlegroup.easyarmorstands.common.handle;

import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.util.Map;

/**
 * Something that can be selected by the user and edited using its manipulators.
 */
public interface Handle {
    @NotNull Session session();

    void addManipulator(String name, Manipulator manipulator);

    Map<String, Manipulator> getManipulators();

    /**
     * Refresh properties of the handle, such as its {@link #getPosition() position}.
     * Called before starting or updating a manipulator.
     */
    void update(boolean active);

    void click();

    void select(Manipulator manipulator);

    /**
     * The position of the handle.
     * May be outdated if {@link #update} was not just called.
     *
     * @return The position.
     */
    Vector3dc getPosition();

    Component title();

    Component subtitle();
}
