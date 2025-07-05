package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.util.ItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class SpawnSlot implements MenuSlot {
    private final ElementType type;
    private final ItemTemplate template;
    private final TagResolver resolver;

    public SpawnSlot(ElementType type, ItemTemplate template, TagResolver resolver) {
        this.type = type;
        this.template = template;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return template.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isLeftClick()) {
            ElementSpawnRequest spawnRequest = EasyArmorStands.get().elementSpawnRequest(type);
            spawnRequest.setPlayer(click.player());
            Element element = spawnRequest.spawn();

            Session session = click.session();
            if (session != null) {
                ElementSelectionNode selectionNode = session.findNode(ElementSelectionNode.class);
                if (selectionNode != null) {
                    if (element instanceof SelectableElement) {
                        selectionNode.selectElement((SelectableElement) element);
                    }
                }
            }

            click.close();
        }
    }
}
