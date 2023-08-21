package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;

@FunctionalInterface
public interface DisplayRootNodeFactory<T extends Display> {
    DisplayRootNode createRootNode(Session session, Component name, DisplayElement<T> element);
}
