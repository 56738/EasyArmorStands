package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.bone.PositionBone;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CarryNode extends EditNode {
    protected final Session session;
    protected final PositionBone bone;
    protected final Vector3d initialPosition = new Vector3d();
    protected final Vector3d initialOffset = new Vector3d();
    protected final Vector3d currentPosition = new Vector3d();
    protected final Cursor3D cursor;

    public CarryNode(Session session, PositionBone bone) {
        super(session);
        this.session = session;
        this.bone = bone;
        this.cursor = new Cursor3D(session);
    }

    @Override
    public void onEnter(EnterContext context) {
        Vector3dc position = Util.toVector3d(session.player().getLocation());
        initialPosition.set(bone.getPosition());
        initialPosition.sub(position, initialOffset);
        cursor.start(initialPosition);
    }

    @Override
    protected void abort() {
        bone.setPosition(initialPosition);
    }

    protected void update() {
        cursor.update(false);
        Player player = session.player();
        Location location = player.getLocation();
        Vector3dc position = Util.toVector3d(location);
        if (location.getPitch() > 80) {
            currentPosition.set(position);
        } else {
            Vector3dc cursor = this.cursor.get();
            currentPosition.x = session.snapPosition(cursor.x() - initialPosition.x) + initialPosition.x;
            currentPosition.y = session.snapPosition(cursor.y() - initialPosition.y) + initialPosition.y;
            currentPosition.z = session.snapPosition(cursor.z() - initialPosition.z) + initialPosition.z;
            if (!player.isFlying()) {
                currentPosition.y = position.y() + initialOffset.y;
            }
        }
    }

    protected void apply() {
        bone.setPosition(currentPosition);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        update();
        apply();
    }

    @Override
    public void onExit(ExitContext context) {
        cursor.stop();
        bone.commit();
    }

    @Override
    public boolean isValid() {
        return bone.isValid();
    }
}
