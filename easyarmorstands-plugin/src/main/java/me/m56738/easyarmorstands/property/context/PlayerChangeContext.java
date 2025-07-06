package me.m56738.easyarmorstands.property.context;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.group.property.GroupPropertyContainer;
import me.m56738.easyarmorstands.history.ChangeTracker;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class PlayerChangeContext implements ManagedChangeContext {
    private final Player player;
    private final ChangeTracker tracker;

    public PlayerChangeContext(Player player) {
        this.player = player;
        this.tracker = EasyArmorStandsPlugin.getInstance().getHistory(player).getTracker();
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PropertyContainer getProperties(Element element) {
        return new TrackedPropertyContainer(element, tracker, player);
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
