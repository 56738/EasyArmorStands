package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.Locale;

public class SpawnSlot implements MenuSlot {
    private final ElementType type;
    private final ItemTemplate template;
    private final TagResolver resolver;

    public SpawnSlot(ElementType type, ItemTemplate template, TagResolver resolver) {
        this.type = type;
        this.template = template;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return template.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
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
                location.setX(session.snapper().snapPosition(location.getX()));
                location.setY(session.snapper().snapPosition(location.getY()));
                location.setZ(session.snapper().snapPosition(location.getZ()));
                location.setYaw((float) session.snapper().snapAngle(location.getYaw()));
                location.setPitch((float) session.snapper().snapAngle(location.getPitch()));
            }

            PropertyMap properties = new PropertyMap();
            properties.put(EntityPropertyTypes.LOCATION, location);
            type.applyDefaultProperties(properties);

            EasPlayer context = new EasPlayer(player);
            if (!context.canCreateElement(type, properties)) {
                return;
            }

            Element element = type.createElement(properties);
            if (element == null) {
                return;
            }

            context.history().push(new ElementCreateAction(element));

            if (session != null) {
                ElementSelectionNode selectionNode = session.findNode(ElementSelectionNode.class);
                if (selectionNode != null) {
                    if (element instanceof SelectableElement) {
                        selectionNode.selectElement((SelectableElement) element);
                    }
                }
            }

            click.close();
        }
    }
}
