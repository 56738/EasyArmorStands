package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ItemTemplate;
import org.bukkit.Location;
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
            EasPlayer player = click.player();
            Vector3d offset = player.eyeMatrix().transformDirection(0, 0, 2, new Vector3d());
            if (!player.isFlying()) {
                offset.y = 0;
            }
            Location location = player.get().getLocation().add(offset.x, offset.y, offset.z);
            location.setYaw(location.getYaw() + 180);
            Session session = player.session();
            if (session != null) {
                location.setX(session.snap(location.getX()));
                location.setY(session.snap(location.getY()));
                location.setZ(session.snap(location.getZ()));
                location.setYaw((float) session.snapAngle(location.getYaw()));
                location.setPitch((float) session.snapAngle(location.getPitch()));
            }

            PropertyRegistry properties = new PropertyRegistry();
            properties.register(Property.of(PropertyTypes.ENTITY_LOCATION, location));
            type.applyDefaultProperties(properties);

            if (!click.player().canCreateElement(type, properties)) {
                return;
            }

            Element element = type.createElement(properties);
            player.history().push(new ElementCreateAction(element));

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
