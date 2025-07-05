package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.util.ItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.Tag;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class EntityCopySlot implements MenuSlot {
    private final ItemTemplate button;
    private final ItemTemplate item;
    private final TagResolver resolver;

    public EntityCopySlot(ItemTemplate button, ItemTemplate item, Element element) {
        this.button = button;
        this.item = item;
        this.resolver = TagResolver.builder()
                .tag("element", Tag.selfClosingInserting(element.getType().getDisplayName()))
                .build();
    }

    @Override
    public @Nullable ItemStack getItem(Locale locale) {
        return button.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.cursor().getType() == Material.AIR) {
            click.queueTask(() -> click.player().setItemOnCursor(item.render(click.locale(), resolver)));
        } else {
            click.queueTask(() -> click.player().setItemOnCursor(null));
        }
    }
}
