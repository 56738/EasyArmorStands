package me.m56738.easyarmorstands.menu.listener;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.menu.slot.DestroySlot;
import me.m56738.easyarmorstands.menu.slot.EntityCopySlot;
import me.m56738.easyarmorstands.paper.api.element.EntityElement;
import me.m56738.easyarmorstands.paper.api.event.element.ElementMenuLayoutEvent;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.common.permission.Permissions;
import net.kyori.adventure.key.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.TreeSet;

public class ElementMenuListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onElementMenuLayout(ElementMenuLayoutEvent event) {
        Player player = event.getPlayer();
        Element element = event.getElement();
        MenuLayout layout = event.getMenuLayout();

        if (element instanceof DestroyableElement destroyableElement && destroyableElement.canDestroy(PaperPlayer.fromNative(player))) {
            layout.addSlot(new DestroySlot(destroyableElement));
        }
        if (element instanceof EntityElement<?> entityElement && player.hasPermission(Permissions.COPY_ENTITY)) {
            ItemStack item = EntityCopySlot.createItem(entityElement.getEntity());
            if (item != null) {
                layout.addSlot(new EntityCopySlot(entityElement, item));
            }
        }

        TreeSet<PropertyType<?>> propertyTypes = new TreeSet<>(Comparator.comparing(Keyed::key));
        element.getProperties().forEach(property -> propertyTypes.add(property.getType()));
        for (PropertyType<?> type : propertyTypes) {
            type.addToMenu(layout, element);
        }
    }
}
