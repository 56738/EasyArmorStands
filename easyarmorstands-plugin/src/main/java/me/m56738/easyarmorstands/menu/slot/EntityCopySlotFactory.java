package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.capability.egg.SpawnEggCapability;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCopySlotFactory implements MenuSlotFactory {
    private final SimpleItemTemplate buttonTemplate;
    private final SimpleItemTemplate itemTemplate;
    private final SpawnEggCapability spawnEggCapability;

    public EntityCopySlotFactory(SimpleItemTemplate buttonTemplate, SimpleItemTemplate itemTemplate) {
        this.buttonTemplate = buttonTemplate;
        this.itemTemplate = itemTemplate;
        this.spawnEggCapability = EasyArmorStandsPlugin.getInstance().getCapability(SpawnEggCapability.class);
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        if (spawnEggCapability == null) {
            return null;
        }
        Element element = context.element();
        if (element instanceof EntityElement<?>) {
            Entity entity = ((EntityElement<?>) element).getEntity();
            if (context.permissions().test(Permissions.COPY_ENTITY)) {
                ItemStack item = spawnEggCapability.createSpawnEgg(entity);
                if (item != null) {
                    return new EntityCopySlot(
                            buttonTemplate.withFallbackTemplate(item),
                            itemTemplate.withTemplate(item),
                            element);
                }
            }
        }
        return null;
    }
}
