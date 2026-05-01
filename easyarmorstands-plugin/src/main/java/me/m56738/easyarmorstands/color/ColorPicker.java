package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutBuilder;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@NullMarked
public final class ColorPicker {
    private static final MenuLayout LAYOUT = createLayout();

    private ColorPicker() {
    }

    public static Menu create(Player player, ColorPickerContext context) {
        List<MenuButton> buttons = new ArrayList<>();
        buttons.add(new ColorIndicatorButton(context));
        for (ColorAxis axis : ColorAxis.values()) {
            @Subst("red")
            String name = axis.name().toLowerCase(Locale.ROOT);
            buttons.add(new ColorAxisChangeButton(context, EasyArmorStands.key("color_picker/axis/" + name + "/decrement"), MenuIcon.of(Material.GRAY_CONCRETE), axis, -10, -1, -100));
            buttons.add(new ColorAxisButton(context, EasyArmorStands.key("color_picker/axis/" + name), axis));
            buttons.add(new ColorAxisChangeButton(context, EasyArmorStands.key("color_picker/axis/" + name + "/increment"), MenuIcon.of(Material.LIGHT_GRAY_CONCRETE), axis, 10, 1, 100));
        }
        for (DyeColor color : DyeColor.values()) {
            @Subst("light_blue")
            String name = color.name().toLowerCase(Locale.ROOT);
            Key key = EasyArmorStands.key("color_picker/preset/" + name);
            MenuIcon icon = MenuIcon.of(Objects.requireNonNull(Material.matchMaterial(name + "_wool")));
            Component title = Component.translatable("easyarmorstands.color.preset." + name.replace('_', '-'));
            buttons.add(new ColorPresetButton(context, key, icon, title, color.getColor()));
        }

        TranslatableComponent title = Component.translatable("easyarmorstands.menu.color-picker.title");
        return LAYOUT.createMenu(title, player.locale(), buttons);
    }

    private static MenuLayout createLayout() {
        MenuLayoutBuilder builder = new MenuLayoutBuilder();
        builder.addRule(0, 2, ColorIndicatorButton.rule());
        addAxisRules(builder);
        addPresetRules(builder);
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.background(MenuLayout.PRIMARY_BACKGROUND));
            }
        }
        return builder.build();
    }

    private static void addAxisRules(MenuLayoutBuilder builder) {
        int row = 1;
        for (ColorAxis axis : ColorAxis.values()) {
            builder.addRule(row, 1, ColorAxisChangeButton.rule(axis, false));
            builder.addRule(row, 2, ColorAxisButton.rule(axis));
            builder.addRule(row, 3, ColorAxisChangeButton.rule(axis, true));
            row++;
        }
    }

    private static void addPresetRules(MenuLayoutBuilder builder) {
        int row = 0;
        int column = 5;
        for (int i = 0; i < DyeColor.values().length; i++) {
            builder.addRule(row, column, ColorPresetButton.rule());
            column++;
            if (column >= 9) {
                row++;
                column = 5;
            }
        }
    }
}
