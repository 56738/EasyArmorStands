package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class PositionHandle implements Handle {
    private final Session session;
    private final Vector3d position = new Vector3d();
    private final List<Manipulator> manipulators = new ArrayList<>();

    public PositionHandle(Session session) {
        this.session = session;
        this.manipulators.add(new PositionMoveManipulator(this,
                "Move", NamedTextColor.YELLOW));
        this.manipulators.add(new PositionYawManipulator(this,
                "Rotate", NamedTextColor.GOLD));
        this.manipulators.add(new PositionAxisManipulator(this,
                "X", NamedTextColor.RED,
                new Vector3d(1, 0, 0)));
        this.manipulators.add(new PositionAxisManipulator(this,
                "Y", NamedTextColor.GREEN,
                new Vector3d(0, 1, 0)));
        this.manipulators.add(new PositionAxisManipulator(this,
                "Z", NamedTextColor.BLUE,
                new Vector3d(0, 0, 1)));
    }

    @Override
    public void update() {
        EasArmorStand entity = session.getEntity();
        double y = 1.25;
        if (entity.isSmall()) {
            y /= 2;
        }
        entity.getPosition().add(0, y, 0, position);
    }

    @Override
    public List<Manipulator> getManipulators() {
        return manipulators;
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public Component getComponent() {
        return Component.text("Position");
    }

    public Session getSession() {
        return session;
    }
}
