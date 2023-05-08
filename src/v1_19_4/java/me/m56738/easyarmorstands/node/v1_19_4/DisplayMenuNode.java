package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.node.EntityNode;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Display;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayMenuNode extends MenuNode implements EntityNode {
    private final Session session;
    private final Display entity;

    public DisplayMenuNode(Session session, Component name, Display entity) {
        super(session, name);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        float width = entity.getDisplayWidth();
        float height = entity.getDisplayHeight();
        Vector3d position = Util.toVector3d(entity.getLocation());
        if (width != 0 && height != 0) {
            session.showAxisAlignedBox(
                    position.add(0, height / 2, 0, new Vector3d()),
                    new Vector3d(width, height, width),
                    NamedTextColor.GRAY);
        }
        session.showPoint(position, NamedTextColor.YELLOW);
        super.onUpdate(eyes, target);
    }

    @Override
    public Display getEntity() {
        return entity;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && entity.isValid();
    }
}
