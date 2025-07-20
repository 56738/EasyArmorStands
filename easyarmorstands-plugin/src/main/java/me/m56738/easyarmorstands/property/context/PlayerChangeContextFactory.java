package me.m56738.easyarmorstands.property.context;

import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;

public class PlayerChangeContextFactory implements ChangeContextFactory {
    @Override
    public ManagedChangeContext create(Player player) {
        return new PlayerChangeContext(player);
    }
}
