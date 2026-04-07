package me.m56738.easyarmorstands.property.button;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class ProfilePropertyButton extends PropertyButton<ResolvableProfile> {
    public ProfilePropertyButton(Property<ResolvableProfile> property, MenuIcon icon, List<Component> description) {
        super(property, icon, description);
    }

    @Override
    public MenuIcon icon() {
        ItemStack item = super.icon().asItem().clone();
        item.setData(DataComponentTypes.PROFILE, property.getValue());
        return MenuIcon.of(item);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onClick(MenuClickContext context) {
        if (context.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(context.player())
                    .handlePropertyShiftClick(property);
            return;
        }

        ResolvableProfile profile;
        if (context.isLeftClick()) {
//            profile = click.cursor().getData(DataComponentTypes.PROFILE);
            profile = null; // TODO
            if (profile == null) {
                return;
            }
        } else if (context.isRightClick()) {
            profile = Mannequin.defaultProfile();
        } else {
            return;
        }

        if (property.setValue(profile)) {
            property.commit();
        }
    }
}
