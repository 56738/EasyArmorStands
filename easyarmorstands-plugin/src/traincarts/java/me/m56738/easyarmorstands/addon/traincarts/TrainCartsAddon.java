package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.EasConfig;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.util.ConfigUtil;

public class TrainCartsAddon {
    private ItemTemplate browserButtonTemplate;

    public TrainCartsAddon(EasyArmorStands plugin) {
        plugin.getConfiguration().subscribe(this::load);
        TrainCartsListener listener = new TrainCartsListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    private void load(EasConfig config) {
        browserButtonTemplate = ConfigUtil.getButton(config.getConfig(), "menu.element.buttons.traincarts-model-browser");
    }

    public ItemTemplate getBrowserButtonTemplate() {
        return browserButtonTemplate;
    }
}
