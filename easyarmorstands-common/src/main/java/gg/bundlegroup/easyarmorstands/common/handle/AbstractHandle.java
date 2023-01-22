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
    public void start() {
        this.active = false;
        this.manipulator = null;
    }

    @Override
    public void update() {
        if (this.active) {
            Component component = manipulator.update();
            manipulator.show();
            session.getPlayer().sendActionBar(component);
        } else {
            Manipulator bestManipulator = null;
            double bestDistance = Double.POSITIVE_INFINITY;
            for (Manipulator manipulator : manipulators.values()) {
                manipulator.refresh();
                manipulator.showHandles();
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
    public void onRightClick() {
        if (active) {
            active = false;
        } else if (manipulator != null) {
            Vector3dc target = manipulator.getLookTarget();
            if (target != null) {
                active = true;
                manipulator.refresh();
                manipulator.start(target);
            }
        }
    }

    @Override
    public boolean onLeftClick() {
        if (active) {
            manipulator.abort();
            session.getPlayer().sendActionBar(Component.empty());
            active = false;
            return true;
        }
        return false;
    }

    @Override
    public void select(Manipulator manipulator) {
        this.active = true;
        this.manipulator = manipulator;
        manipulator.refresh();
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
