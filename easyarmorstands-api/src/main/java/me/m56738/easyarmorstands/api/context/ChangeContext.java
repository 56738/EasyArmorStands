package me.m56738.easyarmorstands.api.context;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

/**
 * Use {@link EasyArmorStands#getChangeContextFactory()} to create a change context.
 * <p>
 * Change contexts enforce permissions and keep track of changes.
 * Changes are committed to the history when the context is closed.
 */
@NullMarked
public interface ChangeContext {
    Player getPlayer();

    PropertyContainer getProperties(Element element);

    PropertyContainer getProperties(Collection<Element> elements);

    default void commit() {
        commit(null);
    }

    void commit(@Nullable Component description);
}
