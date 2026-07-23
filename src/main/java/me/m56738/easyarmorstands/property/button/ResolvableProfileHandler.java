package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.profile.Profile;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;

import java.util.function.Function;

public class ResolvableProfileHandler implements ButtonHandler {
    private final EasyArmorStandsCommon eas;
    private final Property<Profile> property;

    public ResolvableProfileHandler(EasyArmorStandsCommon eas, Property<Profile> property) {
        this.eas = eas;
        this.property = property;
    }

    public static Function<Property<Profile>, ResolvableProfileHandler> provider(EasyArmorStandsCommon eas) {
        return p -> new ResolvableProfileHandler(eas, p);
    }

    @Override
    public MenuIcon modifyIcon(MenuIcon icon) {
        return MenuIcon.of(icon.asItem().withProfile(property.getValue()));
    }

    @Override
    public void onClick(MenuClickContext context) {
        Player player = context.player();
        if (context.isShiftClick()) {
            eas.getClipboard(player).handlePropertyShiftClick(property);
        } else if (context.isLeftClick()) {
            ItemStack item = player.getItemOnCursor();
            Profile profile = item.getProfile();
            if (profile != null) {
                if (property.setValue(profile)) {
                    property.commit();
                } else {
                    player.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
                }
            } else {
                eas.platform().getScheduler().runTask(() -> {
                    if (player.getItemOnCursor().isEmpty() && player.isCreativeMode()) {
                        player.setItemOnCursor(eas.platform().getItemType(ItemTypeKeys.PLAYER_HEAD).createItemStack()
                                .withProfile(property.getValue()));
                    }
                });
            }
        }
    }
}
