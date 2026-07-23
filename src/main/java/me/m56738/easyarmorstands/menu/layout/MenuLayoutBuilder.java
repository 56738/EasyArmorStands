package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;

import java.util.ArrayList;
import java.util.List;

public class MenuLayoutBuilder {
    private final EasyArmorStandsCommon eas;
    private final List<MenuLayout.RuleEntry> rules = new ArrayList<>();

    public MenuLayoutBuilder(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public void addRule(int row, int column, MenuLayoutRule rule) {
        rules.add(new MenuLayout.RuleEntry(row, column, rule));
    }

    public MenuLayout build() {
        return new MenuLayout(eas, List.copyOf(rules));
    }
}
