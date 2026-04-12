package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class DisplayBoxButton implements MenuButton {
    private static final Key KEY = EasyArmorStands.key("display/box");

    private final Session session;
    private final Node node;

    public DisplayBoxButton(Session session, Node node) {
        this.session = session;
        this.node = node;
    }

    @Override

    public Key key() {
        return KEY;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(Material.BLACK_STAINED_GLASS);
    }

    @Override
    public MenuButtonCategory category() {
        return MenuButtonCategory.FOOTER;
    }

    @Override
    public Component name() {
        return Component.translatable("easyarmorstands.node.display.box");
    }

    @Override
    public List<Component> description() {
        return List.of(
                Component.translatable("easyarmorstands.node.display.box.description"),
                Component.translatable("easyarmorstands.menu.node.left-click-to-select"));
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isLeftClick()) {
            context.closeMenu();
            session.pushNode(node);
        }
    }
}
