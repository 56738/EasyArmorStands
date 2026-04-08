package me.m56738.easyarmorstands.menu.layout;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.menu.MenuCreator;
import me.m56738.easyarmorstands.menu.button.DestroyButton;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlot;
import me.m56738.easyarmorstands.menu.slot.MenuButtonSlot;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

public class MenuLayout {
    private static final MenuButton PRIMARY_BACKGROUND = createBackground(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
    private static final MenuButton SECONDARY_BACKGROUND = createBackground(Material.YELLOW_STAINED_GLASS_PANE);
    public static final MenuLayout DEFAULT = createDefault();
    public static final MenuLayout SIMPLE = createSimple();
    public static final MenuLayout EQUIPMENT = createEquipment();
    private final List<RuleEntry> rules;

    MenuLayout(List<RuleEntry> rules) {
        this.rules = rules;
    }

    public Menu createMenu(Component title, Locale locale, Collection<MenuButton> buttons) {
        MenuCreator creator = new MenuCreator(locale);
        creator.setTitle(title);
        IntSet filledSlots = new IntOpenHashSet();
        LinkedList<MenuButton> queue = new LinkedList<>(buttons);
        for (RuleEntry entry : rules) {
            int row = entry.row();
            int column = entry.column();
            int index = 9 * row + column;
            if (filledSlots.contains(index)) {
                continue;
            }
            MenuLayoutRule rule = entry.rule();
            MenuButton button = matchButton(rule, queue);
            if (button != null) {
                filledSlots.add(index);
                creator.setSlot(index, MenuButtonSlot.toSlot(button));
            }
        }
        return creator.build();
    }

    private @Nullable MenuButton matchButton(MenuLayoutRule rule, Queue<MenuButton> queue) {
        Iterator<MenuButton> iterator = queue.iterator();
        while (iterator.hasNext()) {
            MenuButton button = iterator.next();
            if (rule.matches(button)) {
                iterator.remove();
                return button;
            }
        }
        return rule.fallback();
    }

    record RuleEntry(int row, int column, MenuLayoutRule rule) {
    }

    @SuppressWarnings("UnstableApiUsage")
    private static MenuButton createBackground(Material material) {
        ItemStack item = ItemStack.of(material);
        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay()
                .hideTooltip(true)
                .build());
        return MenuSlotButton.toButton(
                EasyArmorStands.key("background"),
                new BackgroundSlot(item));
    }

    private static MenuLayout createDefault() {
        MenuLayoutBuilder builder = new MenuLayoutBuilder();
        builder.addRule(0, 8, b -> b instanceof DestroyButton);
        for (int column = 0; column < 9; column++) {
            builder.addRule(0, column, MenuLayoutRule.category(MenuButtonCategory.HEADER));
        }
        for (int column = 0; column < 9; column++) {
            builder.addRule(5, column, MenuLayoutRule.category(MenuButtonCategory.FOOTER));
        }
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.matchAll());
            }
        }
        for (int column = 0; column < 9; column++) {
            builder.addRule(0, column, MenuLayoutRule.background(SECONDARY_BACKGROUND));
        }
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.background(PRIMARY_BACKGROUND));
            }
        }
        for (int column = 0; column < 9; column++) {
            builder.addRule(5, column, MenuLayoutRule.background(SECONDARY_BACKGROUND));
        }
        return builder.build();
    }

    private static MenuLayout createEquipment() {
        MenuLayoutBuilder builder = new MenuLayoutBuilder();
        builder.addRule(0, 8, b -> b instanceof DestroyButton);
        for (int column = 0; column < 9; column++) {
            builder.addRule(0, column, MenuLayoutRule.category(MenuButtonCategory.HEADER));
        }
        for (int column = 4; column < 9; column++) {
            builder.addRule(5, column, MenuLayoutRule.category(MenuButtonCategory.FOOTER));
        }
        builder.addRule(2, 1, MenuLayoutRule.equipmentSlot(EquipmentSlot.HEAD));
        builder.addRule(3, 0, MenuLayoutRule.equipmentSlot(EquipmentSlot.OFF_HAND));
        builder.addRule(3, 1, MenuLayoutRule.equipmentSlot(EquipmentSlot.CHEST));
        builder.addRule(3, 2, MenuLayoutRule.equipmentSlot(EquipmentSlot.HAND));
        builder.addRule(4, 1, MenuLayoutRule.equipmentSlot(EquipmentSlot.LEGS));
        builder.addRule(5, 1, MenuLayoutRule.equipmentSlot(EquipmentSlot.FEET));
        for (int row = 1; row < 5; row++) {
            for (int column = 4; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.matchAll());
            }
        }
        for (int column = 0; column < 9; column++) {
            builder.addRule(0, column, MenuLayoutRule.background(SECONDARY_BACKGROUND));
        }
        for (int row = 1; row < 5; row++) {
            for (int column = 0; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.background(PRIMARY_BACKGROUND));
            }
        }
        for (int column = 0; column < 4; column++) {
            builder.addRule(5, column, MenuLayoutRule.background(PRIMARY_BACKGROUND));
        }
        for (int column = 4; column < 9; column++) {
            builder.addRule(5, column, MenuLayoutRule.background(SECONDARY_BACKGROUND));
        }
        return builder.build();
    }

    private static MenuLayout createSimple() {
        MenuLayoutBuilder builder = new MenuLayoutBuilder();
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.matchAll());
            }
        }
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 9; column++) {
                builder.addRule(row, column, MenuLayoutRule.background(PRIMARY_BACKGROUND));
            }
        }
        return builder.build();
    }
}
