package me.m56738.easyarmorstands.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemType;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class TrainCartsModelListingButton implements MenuButton {
    public static final Key KEY = Key.key("easyarmorstands", "traincarts/model_browser");

    private final Plugin plugin;
    private final EasyArmorStandsCommon eas;
    private final Element element;

    public TrainCartsModelListingButton(Plugin plugin, EasyArmorStandsCommon eas, Element element) {
        this.plugin = plugin;
        this.eas = eas;
        this.element = element;
    }

    @Override
    public Key key() {
        return KEY;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public MenuIcon icon() {
        return MenuIcon.of(PaperItemType.fromNative(ItemType.BOOK));
    }

    @Override
    public MenuButtonCategory category() {
        return MenuButtonCategory.HEADER;
    }

    @Override
    public Component name() {
        return Component.translatable("easyarmorstands.menu.traincarts.model-browser.open");
    }

    @Override
    public List<Component> description() {
        return List.of(Component.translatable("easyarmorstands.menu.traincarts.model-browser.description"));
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (!context.isLeftClick()) {
            return;
        }
        Player player = context.player();
        eas.platform().getScheduler().runTask(() -> {
            player.setItemOnCursor(null);
            TrainCarts.plugin.getModelListing().buildDialog(PaperPlayer.toNative(player), plugin)
                    .cancelOnRootRightClick()
                    .show()
                    .thenAccept(result -> {
                        if (element instanceof MenuElement menuElement) {
                            if (result.cancelledWithRootRightClick()) {
                                menuElement.openMenu(player);
                            } else if (result.success()) {
                                menuElement.openMenu(player);
                                player.setItemOnCursor(PaperItemStack.fromNative(result.selectedItem()));
                            }
                        }
                    });
        });
    }
}
