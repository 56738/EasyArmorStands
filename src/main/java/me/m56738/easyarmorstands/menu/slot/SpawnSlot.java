package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector3d;

import java.util.Locale;

public class SpawnSlot implements MenuSlot {
    private final ElementType type;
    private final ItemTemplate template;

    public SpawnSlot(ElementType type, ItemTemplate template) {
        this.type = type;
        this.template = template;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return template.render(locale);
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isLeftClick()) {
            Player player = click.player();
            Vector3d offset = click.eyeMatrix().transformDirection(0, 0, 2, new Vector3d());
            if (!player.isFlying()) {
                offset.y = 0;
            }
            Location location = player.getLocation().add(offset.x, offset.y, offset.z);
            location.setYaw(location.getYaw() + 180);
            Session session = click.session();
            if (session != null) {
                location.setX(session.snapPosition(location.getX()));
                location.setY(session.snapPosition(location.getY()));
                location.setZ(session.snapPosition(location.getZ()));
                location.setYaw((float) session.snapAngle(location.getYaw()));
                location.setPitch((float) session.snapAngle(location.getPitch()));
            }

            PropertyMap properties = new PropertyMap();
            properties.put(PropertyTypes.ENTITY_LOCATION, location);
            type.applyDefaultProperties(properties);

            EasPlayer context = new EasPlayer(player);
            if (!context.canCreateElement(type, properties)) {
                return;
            }

            Element element = type.createElement(properties);
            context.history().push(new ElementCreateAction(element));

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
