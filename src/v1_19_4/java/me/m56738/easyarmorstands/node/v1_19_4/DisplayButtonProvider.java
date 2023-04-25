package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.session.EntityButtonProvider;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

public class DisplayButtonProvider implements EntityButtonProvider {
    private final JOMLMapper mapper;

    public DisplayButtonProvider(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DisplayButton createButton(Session session, Entity entity) {
        if (!(entity instanceof Display)) {
            return null;
        }
        Display display = (Display) entity;
        return new DisplayButton(session, display, mapper);
    }
}
