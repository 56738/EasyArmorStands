package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.node.v1_19_4.*;
import me.m56738.easyarmorstands.session.EntityButtonPriority;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class DisplaySessionListener implements Listener {
    private final JOMLMapper mapper;

    public DisplaySessionListener(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @EventHandler
    public void onInitialize(SessionInitializeEvent event) {
        register(event.getSession(), Display.class, DisplayRootNode::new, EntityButtonPriority.LOW);
        register(event.getSession(), ItemDisplay.class, ItemDisplayRootNode::new, EntityButtonPriority.NORMAL);
        register(event.getSession(), BlockDisplay.class, BlockDisplayRootNode::new, EntityButtonPriority.NORMAL);
    }

    private <T extends Display> void register(Session session, Class<T> type, DisplayRootNodeFactory<T> factory, EntityButtonPriority priority) {
        session.addProvider(new DisplayButtonProvider<>(type, mapper, factory, priority));
    }
}
