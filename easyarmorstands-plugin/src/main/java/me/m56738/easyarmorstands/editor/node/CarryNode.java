package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.editor.node.EditorAxisNode;
import me.m56738.easyarmorstands.util.Cursor3D;
import net.kyori.adventure.text.Component;

public class CarryNode extends EditorAxisNode {
    protected final Session session;
    protected final CarryAxis carryAxis;
    protected final Cursor3D cursor;
    private final Component name;

    public CarryNode(Session session, CarryAxis carryAxis, Component name) {
        super(session, carryAxis);
        this.session = session;
        this.carryAxis = carryAxis;
        this.cursor = new Cursor3D(session);
        this.name = name;
    }

    @Override
    public void onEnter(EnterContext context) {
        carryAxis.start(session.eyeRay());
        cursor.start(carryAxis.getPosition());
    }

    @Override
    public void onUpdate(UpdateContext context) {
        cursor.update(false);
        carryAxis.update(session.eyeRay());
        session.setActionBar(name);
    }

    @Override
    public void onExit(ExitContext context) {
        super.onExit(context);
        cursor.stop();
    }
}
