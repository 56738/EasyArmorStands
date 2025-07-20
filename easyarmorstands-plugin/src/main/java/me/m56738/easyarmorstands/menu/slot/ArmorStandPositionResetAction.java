package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;

import java.util.function.Consumer;

public class ArmorStandPositionResetAction implements Consumer<MenuClick> {
    private final ArmorStandElement element;

    public ArmorStandPositionResetAction(ArmorStandElement element) {
        this.element = element;
    }

    @Override
    public void accept(MenuClick click) {
        try (ManagedChangeContext context = EasyArmorStands.get().changeContext().create(PaperPlayer.fromNative(click.player()))) {
            Property<Location> property = context.getProperties(element).get(EntityPropertyTypes.LOCATION);
            property.setValue(property.getValue().withRotation(0, 0));
        }
    }
}
