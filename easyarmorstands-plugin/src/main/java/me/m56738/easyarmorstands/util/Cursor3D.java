package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor3D {
    private final Session session;
    private final PointParticle particle;

    private final Vector3d cursor = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor3D(Session session) {
        this.session = session;
        this.particle = session.particleProvider().createPoint();
    }

    public void start(Vector3dc cursor) {
        this.current.set(cursor);
        refresh();
        particle.setPosition(current);
        particle.setColor(ParticleColor.YELLOW);
        session.addParticle(particle);
    }

    private void refresh() {
        session.eyeRay().inverseMatrix().transformPosition(current, cursor);
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            session.eyeMatrix().transformPosition(cursor, current);
        }
        particle.setPosition(current);
    }

    public Vector3dc get() {
        return current;
    }

    public void reset() {
        cursor.set(0, 0, 2);
        session.eyeMatrix().transformPosition(cursor, current);
    }

    public void stop() {
        session.removeParticle(particle);
    }
}
