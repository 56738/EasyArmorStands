package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class AbstractChangeContextNode extends AbstractNode {
    private final ManagedChangeContext context;

    public AbstractChangeContextNode(Session session) {
        super(session);
        this.context = session.getEasyArmorStands().changeContext().create(session.player());
    }

    public final ChangeContext getContext() {
        return context;
    }

    @Override
    public void onRemove(@NotNull RemoveContext context) {
        this.context.close();
        super.onRemove(context);
    }
}
