package me.m56738.easyarmorstands.itemsadder;

import dev.lone.itemsadder.api.CustomEntity;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomMob;
import dev.lone.itemsadder.api.CustomPlayer;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditElementEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemsAdderListener implements Listener {
    @EventHandler
    public void onDiscover(PlayerDiscoverElementEvent event) {
        if (isItemsAdderElement(event.getElement())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEdit(PlayerEditElementEvent event) {
        if (isItemsAdderElement(event.getElement())) {
            event.setCancelled(true);
        }
    }

    private boolean isItemsAdderElement(Element element) {
        if (!(element instanceof EntityElement)) {
            return false;
        }
        Entity entity = ((EntityElement<?>) element).getEntity();
        return isItemsAdderEntity(entity);
    }

    private boolean isItemsAdderEntity(Entity entity) {
        return CustomFurniture.byAlreadySpawned(entity) != null
                || CustomEntity.byAlreadySpawned(entity) != null
                || CustomMob.byAlreadySpawned(entity) != null
                || CustomPlayer.byAlreadySpawned(entity) != null;
    }
}
