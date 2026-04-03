package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MenuProvider {
    @NotNull
    Menu createColorPicker(@NotNull Player player, @NotNull ColorPickerContext context);

    @NotNull
    MenuFactoryBuilder menuFactoryBuilder();

    @NotNull
    MenuContext context(@NotNull Player player);

    @NotNull
    MenuContext context(@NotNull Player player, @NotNull Element element);

    MenuContext context(@NotNull Session session, @NotNull Element element);
}
