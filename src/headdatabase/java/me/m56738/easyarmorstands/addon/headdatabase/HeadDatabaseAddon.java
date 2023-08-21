package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class HeadDatabaseAddon implements Addon {
    private ItemTemplate buttonTemplate;
    private boolean buttonTemplateValid;

    @Override
    public boolean isSupported() {
        return Bukkit.getPluginManager().getPlugin("HeadDatabase") != null;
    }

    @Override
    public String getName() {
        return "HeadDatabase";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        load(plugin.getConfig());
        plugin.getServer().getPluginManager().registerEvents(new HeadDatabaseListener(this, plugin), plugin);
    }

    @Override
    public void reload(EasyArmorStands plugin) {
        load(plugin.getConfig());
    }

    private void load(ConfigurationSection config) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack item = api.getItemHead("227");
        if (item != null) {
            buttonTemplateValid = true;
        } else {
            item = new ItemStack(Material.STONE);
        }
        buttonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.headdb", item);
    }

    public ItemTemplate getButtonTemplate() {
        if (!buttonTemplateValid) {
            load(EasyArmorStands.getInstance().getConfig());
        }
        return buttonTemplate;
    }
}
