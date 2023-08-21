package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;

public interface Node {
    /**
     * Called when the node is entered (activated).
     * This node is now on the top of the stack.
     * A node can only be entered after it was {@link #onAdd(AddContext) added} to the stack.
     *
     * @param context Contains accessors for relevant data.
     */
    void onEnter(EnterContext context);

    /**
     * Called when the node is exited (deactivated).
     * This node is no longer on the top of the stack.
     * The node may be {@link #onRemove(RemoveContext) removed} from the stack after being exited.
     *
     * @param context Contains accessors for relevant data.
     */
    void onExit(ExitContext context);

    /**
     * Called when the node is added to the stack.
     *
     * @param context Contains accessors for relevant data.
     */
    default void onAdd(AddContext context) {
    }

    /**
     * Called when the node is removed from the stack.
     *
     * @param context Contains accessors for relevant data.
     */
    default void onRemove(RemoveContext context) {
    }

    /**
     * Called every tick while this node is active (on the top of the stack).
     *
     * @param context Contains accessors for relevant data.
     */
    void onUpdate(UpdateContext context);

    /**
     * Called every tick while this node is inactive (not on the top of the stack).
     *
     * @param context Contains accessors for relevant data.
     */
    default void onInactiveUpdate(UpdateContext context) {
    }

    /**
     * Called when the user clicks.
     *
     * @param context Contains accessors for relevant data.
     * @return Whether the click was handled.
     */
    boolean onClick(ClickContext context);

    boolean isValid();
}
