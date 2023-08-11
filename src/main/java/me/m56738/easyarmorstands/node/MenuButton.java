package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;

public interface MenuButton {
    Button createButton();

    void onClick(Session session);
}
