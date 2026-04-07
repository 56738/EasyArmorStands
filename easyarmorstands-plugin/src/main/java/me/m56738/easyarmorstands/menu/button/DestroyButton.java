package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class DestroyButton implements MenuButton {
    private final DestroyableElement element;

    public DestroyButton(DestroyableElement element) {
        this.element = element;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(Material.TNT);
    }

    @Override
    public Component name() {
        return Component.translatable("easyarmorstands.menu.destroy");
    }

    @Override
    public List<Component> description() {
        return List.of(Component.translatable("easyarmorstands.menu.destroy.description"));
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (!context.isLeftClick()) {
            return;
        }

        ChangeContext changeContext = new EasPlayer(context.player());
        if (!changeContext.canDestroyElement(element)) {
            return;
        }

        changeContext.history().push(new ElementDestroyAction(element));
        element.destroy();
        context.closeMenu();
    }
}
