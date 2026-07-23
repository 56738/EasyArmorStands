package me.m56738.easyarmorstands.editor.box;

import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface BoundingBoxEditor {
    BoundingBox getBoundingBox();

    boolean canMove(Player player);

    boolean canResize(Player player);

    BoundingBoxEditorSession start();
}
