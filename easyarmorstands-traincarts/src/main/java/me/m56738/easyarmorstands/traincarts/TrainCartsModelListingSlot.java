package me.m56738.easyarmorstands.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class TrainCartsModelListingSlot implements MenuSlot {
    private final ItemTemplate itemTemplate;
    private final MenuElement element;
    private final TagResolver resolver;

    public TrainCartsModelListingSlot(ItemTemplate itemTemplate, MenuElement element, TagResolver resolver) {
        this.itemTemplate = itemTemplate;
        this.element = element;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }
        Player player = click.player();
        click.queueTask(() -> {
            player.setItemOnCursor(null);
            TrainCarts.plugin.getModelListing().buildDialog(player, EasyArmorStands.getInstance())
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
