package gg.bundlegroup.easyarmorstands;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3d;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneAimManipulator extends Manipulator {
    private final BoneHandle handle;
    private final Vector3d origin = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d lastDirection = new Vector3d();
    private final Quaterniond difference = new Quaterniond();
    private final Matrix3d current = new Matrix3d();

    public BoneAimManipulator(BoneHandle handle, String name, RGBLike color) {
        super(name, color);
        this.handle = handle;
    }

    private void updateDirection(Vector3dc cursor) {
        cursor.sub(origin, currentDirection);
    }

    @Override
    public void start() {
        handle.getSession().getCursor().start(handle.getPosition(), true);
        this.origin.set(handle.getAnchor());
        this.current.set(handle.getRotation());
        updateDirection(handle.getSession().getCursor().get());
        lastDirection.set(currentDirection);
    }

    @Override
    public void update() {
        updateDirection(handle.getSession().getCursor().get());
        lastDirection.rotationTo(currentDirection, difference);
        lastDirection.set(currentDirection);
        handle.getSession().getPlayer().showLine(origin, handle.getSession().getCursor().get(), NamedTextColor.WHITE, false);
        current.rotateLocal(difference);
        handle.setRotation(current);
    }
}
