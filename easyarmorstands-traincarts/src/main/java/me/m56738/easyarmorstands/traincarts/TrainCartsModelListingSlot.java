package me.m56738.easyarmorstands.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TrainCartsModelListingSlot implements MenuSlot {
    private final SimpleItemTemplate itemTemplate;
    private final MenuElement element;
    private final TagResolver resolver;

    public TrainCartsModelListingSlot(SimpleItemTemplate itemTemplate, MenuElement element, TagResolver resolver) {
        this.itemTemplate = itemTemplate;
        this.element = element;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }
        Player player = click.player();
        click.queueTask(() -> {
            player.setItemOnCursor(null);
            TrainCarts.plugin.getModelListing().buildDialog(player, EasyArmorStandsPlugin.getInstance())
                    .cancelOnRootRightClick()
                    .show()
                    .thenAccept(result -> {
                        if (result.cancelledWithRootRightClick()) {
                            element.openMenu(player);
                        } else if (result.success()) {
                            element.openMenu(player);
                            player.setItemOnCursor(result.selectedItem());
                        }
                    });
        });
    }
}
