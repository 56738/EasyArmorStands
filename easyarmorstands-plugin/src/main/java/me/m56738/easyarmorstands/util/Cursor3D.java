package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;

public class Cursor3D {
    private final Session session;
    private final PointParticle particle;

    private final Vector3d cursor = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor3D(Session session) {
        this.session = session;
        this.particle = session.particleProvider().createPoint();
    }

    public void start(EnterContext context, Vector3dc cursor) {
        context.eyeRay().inverseMatrix().transformPosition(cursor, this.cursor);
        this.current.set(cursor);
        particle.setPosition(current);
        particle.setColor(ParticleColor.YELLOW);
        session.addParticle(particle);
    }

    public void update(UpdateContext context) {
        context.eyeRay().matrix().transformPosition(cursor, current);
        particle.setPosition(current);
    }

    public Vector3dc get() {
        return current;
    }

    public void stop() {
        session.removeParticle(particle);
    }
}
