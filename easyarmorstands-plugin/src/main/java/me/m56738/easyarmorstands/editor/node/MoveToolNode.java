package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.MoveToolSession;
import me.m56738.easyarmorstands.util.Cursor3D;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class MoveToolNode extends ToolNode {
    protected final Session session;
    protected final MoveToolSession toolSession;
    protected final Cursor3D cursor;
    private final Vector3dc initialPosition;
    private final Vector3d change = new Vector3d();

    public MoveToolNode(Session session, MoveToolSession toolSession, Component name, Vector3dc position) {
        super(session, toolSession, name);
        this.session = session;
        this.toolSession = toolSession;
        this.cursor = new Cursor3D(session);
        this.initialPosition = new Vector3d(position);
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        cursor.start(context, initialPosition);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        cursor.update(context);
        cursor.get().sub(initialPosition, change);
        toolSession.snapChange(change, session.snapper());
        toolSession.setChange(change);
        super.onUpdate(context);
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        cursor.stop();
    }
}
