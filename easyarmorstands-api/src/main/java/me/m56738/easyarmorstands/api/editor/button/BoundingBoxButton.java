package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.util.BoundingBoxProvider;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class BoundingBoxButton implements Button {
    private final Session session;
    private final BoundingBoxProvider boxProvider;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final PointParticle pointParticle;
    private final BoundingBoxParticle boxParticle;
    private double scale;
    private BoundingBox box;
    private boolean previewVisible;
    private boolean boxVisible;

    public BoundingBoxButton(@NotNull Session session, @NotNull BoundingBoxProvider boxProvider, @NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider) {
        this.session = session;
        this.pointParticle = session.particleProvider().createPoint();
        this.boxProvider = boxProvider;
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
        this.pointParticle.setBillboard(false);
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
    }

    @Override
    public void update() {
        box = BoundingBox.of(boxProvider.getBoundingBox());
        position.set(positionProvider.getPosition());
        rotation.set(rotationProvider.getRotation());
        scale = session.getScale(position);
    }

    @Override
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectPoint(position, scale);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
        intersection = ray.intersectBox(box);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection, -1));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        pointParticle.setPosition(position);
        pointParticle.setRotation(rotation);
        pointParticle.setColor(focused ? ParticleColor.YELLOW : ParticleColor.WHITE);
        pointParticle.setSize(scale / 16);
        boxParticle.setBoundingBox(box);
        boolean showBox = focused && !box.getMinPosition().equals(box.getMaxPosition());
        if (previewVisible && showBox != boxVisible) {
            if (showBox) {
                session.addParticle(boxParticle);
            } else {
                session.removeParticle(boxParticle);
            }
        }
        boxVisible = showBox;
    }

    @Override
    public void showPreview() {
        previewVisible = true;
        session.addParticle(pointParticle);
        if (boxVisible) {
            session.addParticle(boxParticle);
        }
    }

    @Override
    public void hidePreview() {
        previewVisible = false;
        session.removeParticle(pointParticle);
        if (boxVisible) {
            session.removeParticle(boxParticle);
        }
    }
}
