package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutBuilder;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.intellij.lang.annotations.Subst;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SequencedMap;

@NullMarked
public final class ColorPicker {
    public static final SequencedMap<String, RGBColor> PRESETS = new LinkedHashMap<>();

    static {
        PRESETS.put("white", RGBColor.of(0xF9FFFE));
        PRESETS.put("orange", RGBColor.of(0xF9801D));
        PRESETS.put("magenta", RGBColor.of(0xC74EBD));
        PRESETS.put("light_blue", RGBColor.of(0x3AB3DA));
        PRESETS.put("yellow", RGBColor.of(0xFED83D));
        PRESETS.put("lime", RGBColor.of(0x80C71F));
        PRESETS.put("pink", RGBColor.of(0xF38BAA));
        PRESETS.put("gray", RGBColor.of(0x474F52));
        PRESETS.put("light_gray", RGBColor.of(0x9D9D97));
        PRESETS.put("cyan", RGBColor.of(0x169C9C));
        PRESETS.put("purple", RGBColor.of(0x8932B8));
        PRESETS.put("blue", RGBColor.of(0x3C44AA));
        PRESETS.put("brown", RGBColor.of(0x835432));
        PRESETS.put("green", RGBColor.of(0x5E7C16));
        PRESETS.put("red", RGBColor.of(0xB02E26));
        PRESETS.put("black", RGBColor.of(0x1D1D21));
    }

    private ColorPicker() {
    }

    public static Menu create(EasyArmorStandsCommon eas, Player player, ColorPickerContext context) {
        List<MenuButton> buttons = new ArrayList<>();
        buttons.add(new ColorIndicatorButton(context));
        for (ColorAxis axis : ColorAxis.values()) {
            @Subst("red")
            String name = axis.name().toLowerCase(Locale.ROOT);
            buttons.add(new ColorAxisChangeButton(context, EasyArmorStands.key("color_picker/axis/" + name + "/decrement"), MenuIcon.of(context.platform().getItemType(ItemTypeKeys.GRAY_CONCRETE)), axis, -10, -1, -100));
            buttons.add(new ColorAxisButton(context, EasyArmorStands.key("color_picker/axis/" + name), axis));
            buttons.add(new ColorAxisChangeButton(context, EasyArmorStands.key("color_picker/axis/" + name + "/increment"), MenuIcon.of(context.platform().getItemType(ItemTypeKeys.LIGHT_GRAY_CONCRETE)), axis, 10, 1, 100));
        }

        for (Map.Entry<String, RGBColor> entry : PRESETS.entrySet()) {
            @Subst("light_blue")
            String name = entry.getKey();
            Key key = EasyArmorStands.key("color_picker/preset/" + name);
            MenuIcon icon = MenuIcon.of(context.platform().getItemType(Key.key(name + "_wool")));
            Component title = Component.translatable("easyarmorstands.color.preset." + name.replace('_', '-'));
            buttons.add(new ColorPresetButton(context, key, icon, title, entry.getValue()));
        }

        TranslatableComponent title = Component.translatable("easyarmorstands.menu.color-picker.title");
        return createLayout(eas).createMenu(title, player.locale(), buttons);
    }

    private static MenuLayout createLayout(EasyArmorStandsCommon eas) {
        MenuLayoutBuilder builder = new MenuLayoutBuilder(eas);
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
        for (int i = 0; i < 16; i++) {
            builder.addRule(row, column, ColorPresetButton.rule());
            column++;
            if (column >= 9) {
                row++;
                column = 5;
            }
        }
    }
}
