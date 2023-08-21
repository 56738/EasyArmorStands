package me.m56738.easyarmorstands.session.context;

import me.m56738.easyarmorstands.api.editor.context.ExitContext;

public class ExitContextImpl implements ExitContext {
    public static final ExitContextImpl INSTANCE = new ExitContextImpl();

    private ExitContextImpl() {
    }
}
