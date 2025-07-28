package me.m56738.easyarmorstands.common.group.tool;

import me.m56738.easyarmorstands.api.editor.tool.ScalarToolSession;
import me.m56738.easyarmorstands.api.editor.tool.Tool;
import me.m56738.easyarmorstands.api.platform.entity.Player;

import java.util.List;

public abstract class GroupScalarToolSession<S extends ScalarToolSession> extends GroupToolSession<S> implements ScalarToolSession {
    protected GroupScalarToolSession(List<? extends Tool<S>> tools) {
        super(tools);
    }

    @Override
    public boolean canSetValue(Player player) {
        for (ScalarToolSession session : sessions) {
            if (!session.canSetValue(player)) {
                return false;
            }
        }
        return true;
    }
}
