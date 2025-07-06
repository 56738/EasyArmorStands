package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementSpawnRequestImpl implements ElementSpawnRequest {
    private final PropertyMap properties = new PropertyMap();
    private final ElementType type;
    private Player player;

    public ElementSpawnRequestImpl(ElementType type) {
        this.type = type;
    }

    @Override
    public @NotNull ElementType getType() {
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
    public @NotNull PropertyMap getProperties() {
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
            Vector offset = originLocation.getDirection().multiply(2);
            if (!player.isFlying()) {
                offset.setY(0);
            }
            Location location = originLocation.add(offset);
            location.setYaw(location.getYaw() + 180);
            Session session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(player);
            if (session != null) {
                location.setX(session.snapper().snapPosition(location.getX()));
                location.setY(session.snapper().snapPosition(location.getY()));
                location.setZ(session.snapper().snapPosition(location.getZ()));
                location.setYaw((float) session.snapper().snapAngle(location.getYaw()));
                location.setPitch((float) session.snapper().snapAngle(location.getPitch()));
            }
            properties.put(EntityPropertyTypes.LOCATION, location);
        }
        type.applyDefaultProperties(properties);
        properties.putAll(this.properties);

        if (player != null && !EasyArmorStandsPlugin.getInstance().canCreateElement(player, type, properties)) {
            return null;
        }

        Element element = type.createElement(properties);
        if (element == null) {
            return null;
        }

        if (player != null) {
            EasyArmorStandsPlugin.getInstance().getHistory(player).push(new ElementCreateAction(element));
            EasyArmorStandsPlugin.getInstance().getClipboard(player).handleAutoApply(element, player);
        }
        return element;
    }
}
