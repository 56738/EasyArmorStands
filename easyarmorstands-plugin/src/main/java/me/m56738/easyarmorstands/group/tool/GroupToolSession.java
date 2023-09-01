package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.editor.tool.Tool;
import me.m56738.easyarmorstands.api.editor.tool.ToolSession;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupToolSession<S extends ToolSession> implements ToolSession {
    protected final List<S> sessions;

    protected GroupToolSession(List<? extends Tool<S>> tools) {
        sessions = new ArrayList<>(tools.size());
        for (Tool<S> tool : tools) {
            sessions.add(tool.start());
        }
    }

    @Override
    public void revert() {
        for (S session : sessions) {
            session.revert();
        }
    }

    @Override
    public void commit() {
        for (S session : sessions) {
            session.commit();
        }
    }

    @Override
    public boolean isValid() {
        for (S session : sessions) {
            if (session.isValid()) {
                return true;
            }
        }
        return false;
    }
}
