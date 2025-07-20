package me.m56738.easyarmorstands.editor.box;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.util.BoundingBox;

public interface BoundingBoxEditor {
    BoundingBox getBoundingBox();

    boolean canMove(Player player);

    boolean canResize(Player player);

    BoundingBoxEditorSession start();
}
