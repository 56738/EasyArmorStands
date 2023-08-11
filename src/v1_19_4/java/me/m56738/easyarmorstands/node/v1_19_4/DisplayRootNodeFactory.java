package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;

@FunctionalInterface
public interface DisplayRootNodeFactory<T extends Display> {
    DisplayRootNode createRootNode(Session session, Component name, DisplayObject<T> editableObject, T entity);
}
