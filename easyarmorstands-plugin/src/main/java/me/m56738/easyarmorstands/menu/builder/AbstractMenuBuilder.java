package me.m56738.easyarmorstands.menu.builder;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.menu.MenuCreator;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class AbstractMenuBuilder implements MenuBuilder {
    private final List<MenuButton> buttons = new ArrayList<>();

    @Override
    public void addButton(MenuButton button) {
        buttons.add(button);
    }

    protected abstract void build(List<MenuButton> buttons, MenuCreator creator);

    public Menu build(Component title, Locale locale) {
        MenuCreator creator = new MenuCreator(locale);
        creator.setTitle(title);
        creator.setBackground(EasyArmorStandsPlugin.getInstance().getConfiguration().editor.menu.background.createSlot(new MenuSlotContext() {
            @Override
            public Player player() {
                throw new UnsupportedOperationException();
            }

            @Override
            public @Nullable Session session() {
                throw new UnsupportedOperationException();
            }

            @Override
            public @Nullable Element element() {
                throw new UnsupportedOperationException();
            }

            @Override
            public @Nullable PropertyContainer properties() {
                throw new UnsupportedOperationException();
            }

            @Override
            public PropertyContainer properties(Element element) {
                throw new UnsupportedOperationException();
            }

            @Override
            public PermissionChecker permissions() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Locale locale() {
                return locale;
            }

            @Override
            public TagResolver resolver() {
                return TagResolver.empty();
            }

            @Override
            public @Nullable ColorPickerContext colorPicker() {
                throw new UnsupportedOperationException();
            }
        }));
        build(buttons, creator);
        return creator.build();
    }
}
