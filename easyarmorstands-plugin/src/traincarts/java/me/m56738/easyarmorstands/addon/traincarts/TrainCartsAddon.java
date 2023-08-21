package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.util.ConfigUtil;
import org.bukkit.configuration.ConfigurationSection;

public class TrainCartsAddon implements Addon {
    private ItemTemplate browserButtonTemplate;

    @Override
    public boolean isSupported() {
        try {
            Class.forName("com.bergerkiller.bukkit.common.PluginBase");
            Class.forName("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "TrainCarts";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        load(plugin.getConfig());
        TrainCartsListener listener = new TrainCartsListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void reload(EasyArmorStands plugin) {
        load(plugin.getConfig());
    }

    private void load(ConfigurationSection config) {
        browserButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.traincarts-model-browser");
    }

    public ItemTemplate getBrowserButtonTemplate() {
        return browserButtonTemplate;
    }
}
