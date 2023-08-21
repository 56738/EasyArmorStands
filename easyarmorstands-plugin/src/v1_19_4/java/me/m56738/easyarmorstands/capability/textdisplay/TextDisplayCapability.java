package me.m56738.easyarmorstands.capability.textdisplay;

import me.m56738.easyarmorstands.capability.Capability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

@Capability(name = "Text display", optional = true)
public interface TextDisplayCapability {
    Component getText(TextDisplay entity);

    void setText(TextDisplay entity, Component text);
}
