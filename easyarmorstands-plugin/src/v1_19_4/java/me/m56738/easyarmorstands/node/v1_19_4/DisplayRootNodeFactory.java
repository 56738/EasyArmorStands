package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.api.editor.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;

@FunctionalInterface
public interface DisplayRootNodeFactory<T extends Display> {
    DisplayRootNode createRootNode(Session session, Component name, DisplayElement<T> element);
}
