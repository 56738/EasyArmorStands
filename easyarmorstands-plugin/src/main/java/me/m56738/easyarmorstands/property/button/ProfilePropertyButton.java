package me.m56738.easyarmorstands.property.button;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.bukkit.Material;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class ProfilePropertyButton extends PropertyButton<ResolvableProfile> {
    public ProfilePropertyButton(Property<ResolvableProfile> property, PropertyContainer container, SimpleItemTemplate item) {
        super(property, container, item);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    protected SimpleItemTemplate prepareTemplate(SimpleItemTemplate template) {
        ResolvableProfile profile = property.getValue();
        ItemStack item = ItemStack.of(Material.PLAYER_HEAD);
        item.setData(DataComponentTypes.PROFILE, profile);
        return super.prepareTemplate(template).withTemplate(item);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
            return;
        }

        ResolvableProfile profile;
        if (click.isLeftClick()) {
            profile = click.cursor().getData(DataComponentTypes.PROFILE);
            if (profile == null) {
                return;
            }
        } else if (click.isRightClick()) {
            profile = Mannequin.defaultProfile();
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
