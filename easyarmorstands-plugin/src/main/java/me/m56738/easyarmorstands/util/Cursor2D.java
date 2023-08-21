package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor2D {
    private final Session session;
    private final PointParticle particle;

    private final Vector3d origin = new Vector3d();
    private final Vector3d normal = new Vector3d();
    private final Vector3d current = new Vector3d();
    private final Vector2d cursor = new Vector2d();

    public Cursor2D(Session session) {
        this.session = session;
        this.particle = session.particleFactory().createPoint();
    }

    public void start(Vector3dc origin, Vector3dc cursor, Vector3dc normal) {
        this.origin.set(origin);
        this.current.set(cursor);
        this.normal.set(normal);
        particle.setPosition(current);
        particle.setColor(ParticleColor.YELLOW);
        session.addParticle(particle);
        refresh();
    }

    private void refresh() {
        Vector3d cursor = new Vector3d();
        session.eyeMatrix().invertAffine(new Matrix4d()).transformPosition(current, cursor);
        this.cursor.x = cursor.x;
        this.cursor.y = cursor.y;
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            Vector3dc intersection = session.eyeRay(cursor).intersectPlane(origin, normal);
            if (intersection != null) {
                current.set(intersection);
            }
        }
        particle.setPosition(current);
    }

    public Vector3dc get() {
        return current;
    }

    public void stop() {
        session.removeParticle(particle);
    }
}
