package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.m56738.easyarmorstands.EasConfig;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.util.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HeadDatabaseAddon {
    private ItemTemplate buttonTemplate;
    private boolean buttonTemplateValid;

    public HeadDatabaseAddon(EasyArmorStands plugin) {
        plugin.getConfiguration().subscribe(this::load);
        plugin.getServer().getPluginManager().registerEvents(new HeadDatabaseListener(this, plugin), plugin);
    }

    private void load(EasConfig config) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack item = api.getItemHead("227");
        if (item != null) {
            buttonTemplateValid = true;
        } else {
            item = new ItemStack(Material.STONE);
        }
        buttonTemplate = ConfigUtil.getButton(config.getConfig(), "menu.element.buttons.headdb", item);
    }

    public ItemTemplate getButtonTemplate() {
        if (!buttonTemplateValid) {
            load(EasyArmorStands.getInstance().getConfiguration());
        }
        return buttonTemplate;
    }
}
