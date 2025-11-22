package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.event.player.PlayerCommitElementEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A property container which performs permission checks before modifying the value.
 */
public class PermissionCheckedPropertyContainer extends PropertyWrapperContainer {
    private final Element element;
    private final ChangeContext context;

    public PermissionCheckedPropertyContainer(Element element, ChangeContext context) {
        super(element.getProperties());
        this.element = element;
        this.context = context;
    }

    @Override
    protected @NotNull <T> Property<T> wrap(@NotNull Property<T> property) {
        return new PermissionCheckedPropertyWrapper<>(property, element, context);
    }

    @Override
    public void commit(@Nullable Component description) {
        super.commit(description);
        Bukkit.getPluginManager().callEvent(new PlayerCommitElementEvent(context.player(), element));
    }
}
