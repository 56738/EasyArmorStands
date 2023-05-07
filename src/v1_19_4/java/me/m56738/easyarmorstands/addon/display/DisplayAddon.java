package me.m56738.easyarmorstands.addon.display;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.property.v1_19_4.display.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayBrightnessProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTransformationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;

    public DisplayAddon() {
        try {
            mapper = new JOMLMapper();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isSupported() {
        return mapper != null;
    }

    @Override
    public String getName() {
        return "display entity";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        DisplayTransformationProperty transformationProperty = new DisplayTransformationProperty(mapper);
        plugin.getEntityPropertyRegistry().register(transformationProperty);
        plugin.getEntityPropertyRegistry().register(new ItemDisplayItemProperty());
        plugin.getEntityPropertyRegistry().register(new BlockDisplayBlockProperty());
        plugin.getEntityPropertyRegistry().register(new DisplayBrightnessProperty());

        DisplaySessionListener listener = new DisplaySessionListener(mapper, transformationProperty);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
