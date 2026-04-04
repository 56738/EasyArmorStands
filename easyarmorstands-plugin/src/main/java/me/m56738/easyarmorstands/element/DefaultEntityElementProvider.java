package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DefaultEntityElementProvider<E extends Entity> extends SimpleEntityElementProvider<E> {
    public DefaultEntityElementProvider(DefaultEntityElementType<E> type) {
        super(type);
    }

    @Override
    public Element getElement(@NotNull Entity entity) {
        if (entity instanceof Player && !entity.hasMetadata("NPC") && !EasyArmorStandsPlugin.getInstance().getConfiguration().editor.allowPlayers) {
            return null;
        }
        return super.getElement(entity);
    }
}
