package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.Button;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Locale;

public class DefaultEntityButtonProvider implements EntityButtonProvider {
    @Override
    public Button createButton(Session session, Entity entity) {
        if (entity instanceof Player && !entity.hasMetadata("NPC")) {
            return null;
        }

        String typeName = entity.getType().name().toLowerCase(Locale.ROOT).replace("_", "");
        if (!session.getPlayer().hasPermission("easyarmorstands.edit." + typeName)) {
            return null;
        }

        return new DefaultEntityButton(session, entity);
    }

    @Override
    public EntityButtonPriority getPriority() {
        return EntityButtonPriority.LOWEST;
    }
}
