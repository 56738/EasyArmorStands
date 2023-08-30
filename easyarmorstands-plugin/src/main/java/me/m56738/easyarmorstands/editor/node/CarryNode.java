package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.util.Cursor3D;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

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
    public void onEnter(@NotNull EnterContext context) {
        carryAxis.start(context.eyeRay());
        cursor.start(context, carryAxis.getPosition());
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        cursor.update(context);
        carryAxis.update(context.eyeRay());
        context.setActionBar(name);
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        cursor.stop();
    }
}
