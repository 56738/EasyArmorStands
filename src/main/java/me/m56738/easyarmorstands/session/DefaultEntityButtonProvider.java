package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.Button;
import org.bukkit.entity.Entity;

public class DefaultEntityButtonProvider implements EntityButtonProvider {
    @Override
    public Button createButton(Session session, Entity entity) {
        if (entity == session.getPlayer()) {
            return null;
        }

        return new DefaultEntityButton(session, entity);
    }
}
