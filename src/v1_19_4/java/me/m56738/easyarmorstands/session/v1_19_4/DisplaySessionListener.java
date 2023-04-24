package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayNodeProvider;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class DisplaySessionListener implements Listener {
    private final JOMLMapper mapper;

    public DisplaySessionListener(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @EventHandler
    public void onInitialize(SessionInitializeEvent event) {
        event.getSession().addProvider(new DisplayNodeProvider(mapper));
    }
}
