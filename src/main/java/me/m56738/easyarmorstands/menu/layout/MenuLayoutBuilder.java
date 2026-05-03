package me.m56738.easyarmorstands.menu.layout;

import java.util.ArrayList;
import java.util.List;

public class MenuLayoutBuilder {
    private final List<MenuLayout.RuleEntry> rules = new ArrayList<>();

    public void addRule(int row, int column, MenuLayoutRule rule) {
        rules.add(new MenuLayout.RuleEntry(row, column, rule));
    }

    public MenuLayout build() {
        return new MenuLayout(List.copyOf(rules));
    }
}
