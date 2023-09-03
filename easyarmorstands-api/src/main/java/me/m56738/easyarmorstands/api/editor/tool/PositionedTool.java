package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.util.PositionProvider;

public interface PositionedTool<S extends ToolSession> extends Tool<S>, PositionProvider {
}
