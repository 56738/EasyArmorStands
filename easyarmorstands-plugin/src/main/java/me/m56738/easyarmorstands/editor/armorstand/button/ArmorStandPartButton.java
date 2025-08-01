package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandPartNode;
import me.m56738.easyarmorstands.editor.button.NodeFactoryButton;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.lib.joml.Math;
import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ArmorStandPartButton implements NodeFactoryButton {
    private final Session session;
    private final PropertyContainer container;
    private final ArmorStandPart part;
    private final ArmorStandPartInfo partInfo;
    private final ArmorStandElement element;
    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private final Vector3d center = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final LineParticle particle;
    private final Property<Location> locationProperty;
    private final Property<EulerAngle> poseProperty;
    private final Property<ArmorStandSize> sizeProperty;
    private double scale = 1;

    public ArmorStandPartButton(Session session, PropertyContainer container, ArmorStandPart part, ArmorStandElement element) {
        this.session = session;
        this.container = container;
        this.part = part;
        this.partInfo = ArmorStandPartInfo.of(part);
        this.element = element;
        this.particle = session.particleProvider().createLine();
        this.particle.setAxis(Axis.Y);
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.poseProperty = container.get(ArmorStandPropertyTypes.POSE.get(part));
        this.sizeProperty = container.get(ArmorStandPropertyTypes.SIZE);
    }

    @Override
    public void update() {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        double scale = element.getScale();
        this.scale = Math.max(1, scale); // make sure the button doesn't get too small
        // rotation = combination of yaw and pose
        Util.fromEuler(poseProperty.getValue(), rotation).rotateLocalY(-Math.toRadians(location.getYaw()));
        // start = where the bone is attached to the armor stand, depends on yaw
        partInfo.getOffset(size, scale)
                .rotateY(-Math.toRadians(location.getYaw()), start)
                .add(location.getX(), location.getY(), location.getZ());
        // end = where the bone ends, depends on yaw and pose
        partInfo.getLength(size, scale)
                .rotate(rotation, end)
                .add(start);
        // move start down, start-end will be the lower 2/3 of the bone
        start.lerp(end, 1.0 / 3);
        start.lerp(end, 0.5, center);
    }

    @Override
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectLine(start, end, scale);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        particle.setRotation(rotation);
        particle.setCenter(center);
        particle.setLength(center.distance(end) * 2);
        particle.setWidth(Util.LINE_WIDTH * scale);
        particle.setColor(focused ? ParticleColor.YELLOW : ParticleColor.WHITE);
    }

    @Override
    public void showPreview() {
        session.addParticle(particle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(particle);
    }

    @Override
    public @NotNull Component getName() {
        return partInfo.getDisplayName();
    }

    @Override
    public Node createNode() {
        return new ArmorStandPartNode(session, container, part, element);
    }
}
