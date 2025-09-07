package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.history.action.ElementCreateAction;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.Nullable;

public class ElementSpawnRequestImpl implements ElementSpawnRequest {
    private final EasyArmorStandsCommon eas;
    private final PropertyMap properties = new PropertyMap();
    private final ElementType type;
    private @Nullable Player player;

    public ElementSpawnRequestImpl(EasyArmorStandsCommon eas, ElementType type) {
        this.eas = eas;
        this.type = type;
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public @Nullable Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(@Nullable Player player) {
        this.player = player;
    }

    @Override
    public PropertyMap getProperties() {
        return properties;
    }

    @Override
    public @Nullable Element spawn() {
        PropertyMap properties = new PropertyMap();
        if (player != null) {
            Location originLocation;
            if (type.isSpawnedAtEyeHeight()) {
                originLocation = player.getEyeLocation();
            } else {
                originLocation = player.getLocation();
            }
            Vector3d offset = originLocation.direction().mul(2, new Vector3d());
            if (!player.isFlying()) {
                offset.y = 0;
            }
            Location location = originLocation.withOffset(offset).withYaw(originLocation.yaw() + 180);
            Session session = eas.getSessionManager().getSession(player);
            if (session != null) {
                Vector3dc position = location.position();
                location = location.withPosition(new Vector3d(
                        session.snapper().snapPosition(position.x()),
                        session.snapper().snapPosition(position.y()),
                        session.snapper().snapPosition(position.z())));
                location = location.withRotation(
                        (float) session.snapper().snapAngle(location.yaw()),
                        (float) session.snapper().snapAngle(location.pitch()));
            }
            properties.put(EntityPropertyTypes.LOCATION, location);
        }
        type.applyDefaultProperties(properties);
        properties.putAll(this.properties);

        if (player != null && !eas.getPlatform().canCreateElement(player, type, properties)) {
            return null;
        }

        Element element = type.createElement(properties);
        if (element == null) {
            return null;
        }

        if (player != null) {
            eas.getHistoryManager().getHistory(player).push(new ElementCreateAction(eas, element));
            eas.getClipboardManager().getClipboard(player).handleAutoApply(element, player);
        }
        return element;
    }
}
