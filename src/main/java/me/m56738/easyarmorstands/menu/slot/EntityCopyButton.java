package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class EntityCopyButton implements MenuButton {
    private static final Key KEY = EasyArmorStands.key("copy");
    private final EasyArmorStandsCommon eas;
    private final Entity entity;
    private final ElementType type;
    private final ItemStack item;

    public EntityCopyButton(EasyArmorStandsCommon eas, Entity entity, ElementType type, ItemStack item) {
        this.eas = eas;
        this.entity = entity;
        this.type = type;
        this.item = item;
    }

    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(item);
    }

    @Override
    public MenuButtonCategory category() {
        return MenuButtonCategory.FOOTER;
    }

    @Override
    public Component name() {
        return Component.translatable("easyarmorstands.menu.copy");
    }

    @Override
    public List<Component> description() {
        return List.of(Component.translatable("easyarmorstands.menu.copy.description"));
    }

    @Override
    public void onClick(MenuClickContext context) {
        Player player = context.player();
        ItemStack cursor = player.getItemOnCursor();
        if (cursor.isEmpty()) {
            eas.platform().getScheduler().runTask(() -> {
                ItemStack item = eas.createEntitySpawnEgg(entity);
                if (!item.isEmpty()) {
                    item = item.withItemName(GlobalTranslator.render(Component.translatable("easyarmorstands.copy.item.name",
                            NamedTextColor.GOLD,
                            type.getDisplayName()), player.locale()));
                    player.setItemOnCursor(item);
                }
            });
        } else {
            eas.platform().getScheduler().runTask(() -> player.setItemOnCursor(null));
        }
    }
}
