package me.m56738.easyarmorstands.api.editor.layer;

import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.LateUpdateContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import org.jetbrains.annotations.NotNull;

public interface Layer {
    /**
     * Called when the layer is entered (activated).
     * This layer is now on the top of the stack.
     * A layer can only be entered after it was {@link #onAdd(AddContext) added} to the stack.
     *
     * @param context Contains accessors for relevant data.
     */
    void onEnter(@NotNull EnterContext context);

    /**
     * Called when the layer is exited (deactivated).
     * This layer is no longer on the top of the stack.
     * The layer may be {@link #onRemove(RemoveContext) removed} from the stack after being exited.
     *
     * @param context Contains accessors for relevant data.
     */
    void onExit(@NotNull ExitContext context);

    /**
     * Called when the layer is added to the stack.
     *
     * @param context Contains accessors for relevant data.
     */
    default void onAdd(@NotNull AddContext context) {
    }

    /**
     * Called when the layer is removed from the stack.
     *
     * @param context Contains accessors for relevant data.
     */
    default void onRemove(@NotNull RemoveContext context) {
    }

    /**
     * Called every tick while this layer is active (on the top of the stack).
     *
     * @param context Contains accessors for relevant data.
     */
    void onUpdate(@NotNull UpdateContext context);

    /**
     * Called after {@link #onUpdate}.
     *
     * @param context Contains accessors for relevant data.
     */
    default void onLateUpdate(@NotNull LateUpdateContext context) {
    }

    /**
     * Called every tick while this layer is inactive (not on the top of the stack).
     *
     * @param context Contains accessors for relevant data.
     */
    default void onInactiveUpdate(@NotNull UpdateContext context) {
    }

    /**
     * Called when the user clicks.
     *
     * @param context Contains accessors for relevant data.
     * @return Whether the click was handled.
     */
    default boolean onClick(@NotNull ClickContext context) {
        return false;
    }

    boolean isValid();
}
