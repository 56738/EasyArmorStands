package me.m56738.easyarmorstands.common.property.context;

import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.common.group.property.GroupPropertyContainer;
import me.m56738.easyarmorstands.common.history.ChangeTracker;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.common.property.TrackedPropertyContainer;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class PlayerChangeContext implements ManagedChangeContext {
    private final CommonPlatform platform;
    private final Player player;
    private final ChangeTracker tracker;

    public PlayerChangeContext(CommonPlatform platform, Player player, ChangeTracker tracker) {
        this.platform = platform;
        this.player = player;
        this.tracker = tracker;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PropertyContainer getProperties(Element element) {
        return new TrackedPropertyContainer(platform, element, tracker, player);
    }

    @Override
    public PropertyContainer getProperties(Collection<Element> elements) {
        return new GroupPropertyContainer(elements.stream()
                .map(this::getProperties)
                .toList());
    }

    @Override
    public void commit(@Nullable Component description) {
        tracker.commit(description);
    }
}
