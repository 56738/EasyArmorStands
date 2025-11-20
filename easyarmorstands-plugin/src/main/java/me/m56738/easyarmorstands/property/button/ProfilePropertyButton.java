package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.capability.mannequin.MannequinCapability;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ProfilePropertyButton extends PropertyButton<Profile> {
    private final MannequinCapability mannequinCapability;

    public ProfilePropertyButton(Property<Profile> property, PropertyContainer container, SimpleItemTemplate item) {
        super(property, container, item);
        this.mannequinCapability = EasyArmorStandsPlugin.getInstance().getCapability(MannequinCapability.class);
    }

    @Override
    protected SimpleItemTemplate prepareTemplate(SimpleItemTemplate template) {
        Profile profile = property.getValue();
        ItemStack item = mannequinCapability.createProfileItem(profile);
        return super.prepareTemplate(template).withTemplate(item);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
            return;
        }

        Profile profile;
        if (click.isLeftClick()) {
            profile = mannequinCapability.getItemProfile(click.cursor());
            if (profile == null) {
                return;
            }
        } else if (click.isRightClick()) {
            profile = Profile.empty();
        } else {
            return;
        }

        click.queueTask(() -> {
            if (property.setValue(profile)) {
                container.commit();
                click.menu().updateItem(click.index());
            }
        });
    }
}
