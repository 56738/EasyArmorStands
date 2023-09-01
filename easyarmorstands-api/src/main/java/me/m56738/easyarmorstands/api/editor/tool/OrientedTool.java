package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.util.RotationProvider;

public interface OrientedTool<S extends ToolSession> extends Tool<S>, RotationProvider {
}
