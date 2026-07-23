package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A property container which performs permission checks before modifying the value.
 */
public class PermissionCheckedPropertyContainer extends PropertyWrapperContainer {
    private final EasyArmorStandsCommon eas;
    private final Element element;
    private final EasPlayer player;

    public PermissionCheckedPropertyContainer(EasyArmorStandsCommon eas, Element element, EasPlayer player) {
        super(element.getProperties());
        this.eas = eas;
        this.element = element;
        this.player = player;
    }

    @Override
    protected @NotNull <T> Property<T> wrap(@NotNull Property<T> property) {
        return new PermissionCheckedPropertyWrapper<>(property, element, player);
    }

    @Override
    public void commit(@Nullable Component description) {
        super.commit(description);
        eas.eventDispatcher().dispatchCommitElement(player.get(), element);
    }
}
