package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.menu.click.MenuClick;
import me.m56738.easyarmorstands.editor.layer.LayerFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Consumer;

public class LayerSlot implements MenuSlot {
    private final Session session;
    private final LayerFactory layerFactory;
    private final Consumer<MenuClick> resetAction;
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public LayerSlot(Session session, LayerFactory layerFactory, Consumer<MenuClick> resetAction, SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.session = session;
        this.layerFactory = layerFactory;
        this.resetAction = resetAction;
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isLeftClick()) {
            session.pushLayer(layerFactory.createLayer());
            click.close();
        } else if (click.isRightClick()) {
            if (resetAction != null) {
                resetAction.accept(click);
            }
        }
    }
}
