package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.util.EasMath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class ElementSpawnRequestImpl implements ElementSpawnRequest {
    private final EasyArmorStandsCommon eas;
    private final PropertyMap properties = new PropertyMap();
    private final ElementType type;
    private EasPlayer player;

    public ElementSpawnRequestImpl(EasyArmorStandsCommon eas, ElementType type) {
        this.eas = eas;
        this.type = type;
    }

    @Override
    public @NotNull ElementType getType() {
        return type;
    }

    @Override
    public @Nullable Player getPlayer() {
        return player != null ? player.get() : null;
    }

    @Override
    public void setPlayer(@Nullable Player player) {
        this.player = player != null ? new EasPlayer(eas, player) : null;
    }

    @Override
    public @NotNull PropertyMap getProperties() {
        return properties;
    }

    @Override
    public @Nullable Element spawn() {
        PropertyMap properties = new PropertyMap();
        if (player != null) {
            Location originLocation;
            if (type.isSpawnedAtEyeHeight()) {
                originLocation = player.get().eyeLocation();
            } else {
                originLocation = player.get().location();
            }
            Vector3d offset = EasMath.getDirection(originLocation).mul(2);
            if (!player.get().isFlying()) {
                offset.y = 0;
            }
            Location location = originLocation
                    .withPosition(originLocation.position().add(offset, new Vector3d()))
                    .withYaw(originLocation.yaw() + 180);
            Session session = player.session();
            if (session != null) {
                Vector3d position = new Vector3d(location.position());
                session.snapper().snapPosition(position);
                location = location
                        .withPosition(position)
                        .withYaw((float) session.snapper().snapAngle(location.yaw()))
                        .withPitch((float) session.snapper().snapAngle(location.pitch()));
            }
            properties.put(EntityPropertyTypes.LOCATION, location);
        }
        type.applyDefaultProperties(properties);
        properties.putAll(this.properties);

        if (player != null && !player.canCreateElement(type, properties)) {
            return null;
        }

        Element element = type.createElement(properties);
        if (element == null) {
            return null;
        }

        if (player != null) {
            player.history().push(new ElementCreateAction(eas, element));
            player.clipboard().handleAutoApply(element);
        }
        return element;
    }
}
