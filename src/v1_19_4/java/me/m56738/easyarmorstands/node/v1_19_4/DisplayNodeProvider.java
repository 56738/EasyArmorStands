package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.node.ClickableNode;
import me.m56738.easyarmorstands.node.LazyNode;
import me.m56738.easyarmorstands.session.EntityNodeProvider;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

public class DisplayNodeProvider implements EntityNodeProvider {
    private final JOMLMapper mapper;

    public DisplayNodeProvider(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ClickableNode createNode(Session session, Entity entity) {
        if (!(entity instanceof Display display)) {
            return null;
        }
        return new DisplayNode(session, new LazyNode(new DisplayNodeFactory(session, display, mapper)), display);
    }
}
