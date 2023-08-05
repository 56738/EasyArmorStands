package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor3D {
    private final Player player;
    private final Session session;
    private final PointParticle particle;

    private final Vector3d cursor = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor3D(Player player, Session session) {
        this.player = player;
        this.session = session;
        this.particle = EasyArmorStands.getInstance().getCapability(ParticleCapability.class).createPoint(session.getWorld());
    }

    public void start(Vector3dc cursor) {
        this.current.set(cursor);
        refresh();
        particle.setPosition(current);
        particle.setColor(ParticleColor.YELLOW);
        session.addParticle(particle);
    }

    private void refresh() {
        this.cursor.set(current);
        Location eyeLocation = player.getEyeLocation();
        this.cursor.sub(Util.toVector3d(eyeLocation));
        this.cursor.mulTranspose(Util.getRotation(eyeLocation, new Matrix3d()));
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            Location eyeLocation = player.getEyeLocation();
            Util.getRotation(eyeLocation, new Matrix3d()).transform(cursor, current).add(Util.toVector3d(eyeLocation));
        }
        particle.setPosition(current);
    }

    public Vector3dc get() {
        return current;
    }

    public void reset() {
        cursor.set(0, 0, 2);
        Location eyeLocation = player.getEyeLocation();
        Util.getRotation(eyeLocation, new Matrix3d()).transform(cursor, current).add(Util.toVector3d(eyeLocation));
    }

    public void stop() {
        session.removeParticle(particle);
    }
}
