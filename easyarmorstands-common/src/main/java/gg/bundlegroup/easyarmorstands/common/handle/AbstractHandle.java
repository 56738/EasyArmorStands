package gg.bundlegroup.easyarmorstands.common.handle;

import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHandle implements Handle {
    protected final Session session;
    protected final Vector3d position = new Vector3d();
    private final Map<String, Manipulator> manipulators = new HashMap<>();
    private Manipulator manipulator;
    private boolean active;

    public AbstractHandle(Session session) {
        this.session = session;
    }

    @Override
    public void addManipulator(String name, Manipulator manipulator) {
        manipulators.put(name, manipulator);
    }

    @Override
    public Map<String, Manipulator> getManipulators() {
        return manipulators;
    }

    @Override
    public @NotNull Session session() {
        return session;
    }

    @Override
    public void update(boolean active) {
        if (!active) {
            this.active = false;
            this.manipulator = null;
            return;
        }

        if (this.active) {
            manipulator.update(true);
        } else {
            Manipulator bestManipulator = null;
            double bestDistance = Double.POSITIVE_INFINITY;
            for (Manipulator manipulator : manipulators.values()) {
                manipulator.update(false);
                Vector3dc target = manipulator.getLookTarget();
                if (target != null) {
                    double distance = target.distanceSquared(session.getPlayer().getEyePosition());
                    if (distance < bestDistance) {
                        bestManipulator = manipulator;
                        bestDistance = distance;
                    }
                }
            }
            this.manipulator = bestManipulator;
        }
    }

    @Override
    public void click() {
        if (active) {
            active = false;
        } else if (manipulator != null) {
            active = true;
            manipulator.start(manipulator.getLookTarget());
        }
    }

    @Override
    public void select(Manipulator manipulator) {
        this.active = true;
        this.manipulator = manipulator;
        manipulator.start(manipulator.getTarget());
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public Component title() {
        if (manipulator == null) {
            return Component.empty();
        }
        return manipulator.component();
    }

    @Override
    public Component subtitle() {
        return Component.empty();
    }
}
