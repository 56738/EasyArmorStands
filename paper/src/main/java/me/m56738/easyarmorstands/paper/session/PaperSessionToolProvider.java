package me.m56738.easyarmorstands.paper.session;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.Locale;

public class PaperSessionToolProvider implements SessionToolProvider {
    private static final NamespacedKey TOOL_KEY = new NamespacedKey(EasyArmorStands.NAMESPACE, "tool");

    private final EasyArmorStandsCommon eas;

    public PaperSessionToolProvider(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @Override
    public ItemStack createTool(Locale locale) {
        org.bukkit.inventory.ItemStack item = PaperItemStack.toNative(eas.getConfiguration().editor.tool.render(locale));
        item.editPersistentDataContainer(pdc ->
                pdc.set(TOOL_KEY, PersistentDataType.BYTE, (byte) 1));
        return PaperItemStack.fromNative(item);
    }

    @Override
    public boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }
        return PaperItemStack.toNative(item).getPersistentDataContainer().has(TOOL_KEY, PersistentDataType.BYTE);
    }
}
