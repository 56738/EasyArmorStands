package gg.bundlegroup.easyarmorstands.common.bone;

import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.util.Map;

/**
 * Something that can be selected by the user and edited using its manipulators.
 */
public interface Bone {
    @NotNull Session session();

    void addManipulator(String name, Manipulator manipulator);

    Map<String, Manipulator> getManipulators();

    /**
     * Refresh properties of the bone, such as its {@link #getPosition() position}.
     */
    void refresh();

    void start();

    void update();

    void onRightClick();

    boolean onLeftClick();

    void select(Manipulator manipulator);

    /**
     * The position of the bone.
     * May be outdated if {@link #update} was not just called.
     *
     * @return The position.
     */
    Vector3dc getPosition();

    Component title();

    Component subtitle();
}
