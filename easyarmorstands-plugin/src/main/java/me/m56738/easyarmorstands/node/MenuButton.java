package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface MenuButton {
    Button createButton();

    void onClick(Session session, @Nullable Vector3dc cursor);

    Component getName();
}
