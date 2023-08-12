package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.event.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix3d;
import org.joml.Vector3d;

public class SpawnSlot implements MenuSlot {
    private final ElementType type;
    private final ItemStack item;

    public SpawnSlot(ElementType type, ItemStack item) {
        this.type = type;
        this.item = item;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
        click.cancel();
        if (click.isLeftClick()) {
            Player player = click.player();
            Location eyeLocation = player.getEyeLocation();
            Vector3d cursor = Util.getRotation(eyeLocation, new Matrix3d()).transform(0, 0, 2, new Vector3d());
            Vector3d position = new Vector3d(cursor);
            if (!player.isFlying()) {
                position.y = 0;
            }
            Session session = EasyArmorStands.getInstance().getSessionManager().getSession(player);
            Location location = player.getLocation().add(position.x, position.y, position.z);
            location.setYaw(location.getYaw() + 180);
            if (session != null) {
                location.setX(session.snap(location.getX()));
                location.setY(session.snap(location.getY()));
                location.setZ(session.snap(location.getZ()));
                location.setYaw((float) session.snapAngle(location.getYaw()));
                location.setPitch((float) session.snapAngle(location.getPitch()));
            }

            PropertyRegistry properties = new PropertyRegistry();
            properties.register(Property.of(EntityLocationProperty.TYPE, location));
            type.applyDefaultProperties(properties);

            PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }

            Element element = type.createElement(properties);
            EasyArmorStands.getInstance().getHistory(player).push(new ElementCreateAction(element));

            if (session != null) {
                EntitySelectionNode selectionNode = session.findNode(EntitySelectionNode.class);
                if (selectionNode != null) {
                    selectionNode.selectElement(element);
                }
            }

            click.close();
        }
    }
}
