package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;

public abstract class AbstractManipulator implements Manipulator {
    private final Session session;
    private final Component component;
    private final RGBLike color;

    protected AbstractManipulator(Session session, String name, RGBLike color) {
        this.session = session;
        this.component = Component.text(name, TextColor.color(color));
        this.color = color;
    }

    public final Session getSession() {
        return session;
    }

    public final EasPlayer getPlayer() {
        return session.getPlayer();
    }

    @Override
    public final Component getComponent() {
        return component;
    }

    public final RGBLike getColor() {
        return color;
    }
}
