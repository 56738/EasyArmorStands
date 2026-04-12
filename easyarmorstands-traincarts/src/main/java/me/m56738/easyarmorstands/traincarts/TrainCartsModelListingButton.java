package me.m56738.easyarmorstands.traincarts;

import com.bergerkiller.bukkit.tc.TrainCarts;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class TrainCartsModelListingButton implements MenuButton {
    public static final Key KEY = Key.key("easyarmorstands", "traincarts/model_browser");
    private final Element element;

    public TrainCartsModelListingButton(Element element) {
        this.element = element;
    }

    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(Material.BOOK);
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
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            player.setItemOnCursor(null);
            TrainCarts.plugin.getModelListing().buildDialog(player, plugin)
                    .cancelOnRootRightClick()
                    .show()
                    .thenAccept(result -> {
                        if (element instanceof MenuElement menuElement) {
                            if (result.cancelledWithRootRightClick()) {
                                menuElement.openMenu(player);
                            } else if (result.success()) {
                                menuElement.openMenu(player);
                                player.setItemOnCursor(result.selectedItem());
                            }
                        }
                    });
        });
    }
}
