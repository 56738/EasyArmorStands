package me.m56738.easyarmorstands.core.bone;

import me.m56738.easyarmorstands.core.session.Session;
import me.m56738.easyarmorstands.core.tool.Tool;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.util.Map;

/**
 * Something that can be selected by the user and edited using its tools.
 */
public interface Bone {
    @NotNull Session session();

    void addTool(String name, Tool tool);

    Map<String, Tool> getTools();

    /**
     * Refresh properties of the bone, such as its {@link #getPosition() position}.
     */
    void refresh();

    void start();

    void update();

    void onRightClick();

    boolean onLeftClick();

    void select(Tool tool, Vector3dc cursor);

    /**
     * The position of the bone.
     * May be outdated if {@link #update} was not just called.
     *
     * @return The position.
     */
    Vector3dc getPosition();

    Component title();

    Component getName();
}
