package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ScalarToolSession extends ToolSession {
    void setChange(double change);

    @Contract(pure = true)
    double snapChange(double change, @NotNull Snapper context);

    default boolean canSetValue(Player player) {
        return true;
    }

    default void setValue(double value) {
        setChange(value);
    }
}
