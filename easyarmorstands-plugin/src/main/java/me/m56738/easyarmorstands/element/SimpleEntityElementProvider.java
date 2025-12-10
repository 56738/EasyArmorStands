package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityElementProvider implements EntityElementProvider {
    @Override
    public Element getElement(@NotNull Entity entity) {
        if (entity instanceof Player && !entity.hasMetadata("NPC") && !EasyArmorStandsPlugin.getInstance().getConfiguration().editor.allowPlayers) {
            return null;
        }
        return new SimpleEntityElementType<>(entity.getType(), Util.getEntityClass(entity)).getElement(entity);
    }

    @Override
    public @NotNull Priority getPriority() {
        return Priority.LOWEST;
    }
}
