package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuContext;
import me.m56738.easyarmorstands.api.menu.MenuFactoryBuilder;
import me.m56738.easyarmorstands.api.menu.MenuProvider;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.factory.MenuFactoryBuilderImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuProviderImpl implements MenuProvider {
    @Override
    public @NotNull Menu createColorPicker(@NotNull Player player, @NotNull ColorPickerContext context) {
        return EasyArmorStandsPlugin.getInstance().createColorPicker(player, context);
    }

    @Override
    public @NotNull MenuFactoryBuilder menuFactoryBuilder() {
        return new MenuFactoryBuilderImpl();
    }

    @Override
    public @NotNull MenuContext context(@NotNull Player player) {
        return new SimpleMenuContext(new EasPlayer(player));
    }

    @Override
    public @NotNull MenuContext context(@NotNull Player player, @NotNull Element element) {
        return new ElementMenuContext(new EasPlayer(player), null, element);
    }

    @Override
    public @NotNull MenuContext context(@NotNull Session session, @NotNull Element element) {
        return new ElementMenuContext(new EasPlayer(session.player()), session, element);
    }
}
